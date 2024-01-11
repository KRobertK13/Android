package hu.bme.aut.android.mobweb

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.mobweb.adapter.InExAdapter
import hu.bme.aut.android.mobweb.data.InExItem
import hu.bme.aut.android.mobweb.data.MDataBase
import hu.bme.aut.android.mobweb.databinding.ActivityListBinding
import hu.bme.aut.android.mobweb.fragments.NewInExItemDialogFragment
import kotlin.concurrent.thread

class ListActivity : AppCompatActivity(), InExAdapter.InExItemClickListener, NewInExItemDialogFragment.NewInExItemDialogListener {
    private lateinit var binding: ActivityListBinding

    private lateinit var database: MDataBase
    private lateinit var adapter: InExAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = MDataBase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewInExItemDialogFragment().show(
                supportFragmentManager,
                NewInExItemDialogFragment.TAG
            )
        }
        binding
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = InExAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.inExItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onInExItemCreated(newItem: InExItem) {
        thread {
            val insertId = database.inExItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
        SumExpense()
    }
    override fun onItemRemoved(item: InExItem) {
        thread {
            database.inExItemDao().deleteItem(item)
            runOnUiThread {
                adapter.removeItem(item)
            }
        }
        SumExpense()
    }

    private fun SumExpense() {
        thread {
            var maxE: Int = database.taLiItemDao().getAll()[0].spendingLimit
            var targetS:Int = database.taLiItemDao().getAll()[0].targetSavings
            var E: Int = 0
            var S: Int = 0
            for (item in database.inExItemDao().getAll()) when (item.sign) {true -> S+= item.amount; false -> E+= item.amount }
            runOnUiThread {
                if (E >= maxE) {
                    val name = "Alert!"
                    val descriptionText = "You have reached your spending limit!"
                    val builder = NotificationCompat.Builder(this, "MOBWEB_CHANNEL_ID").setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle(name).setContentText(descriptionText).setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                    with(NotificationManagerCompat.from(this)) {
                        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return@runOnUiThread
                        notify(13, builder.build())
                    }
                }
                if (S - E >= targetS) {
                    val name = "Congratulations!"
                    val descriptionText = "You have reached your saving goal!"
                    val builder = NotificationCompat.Builder(this, "MOBWEB_CHANNEL_ID").setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle(name).setContentText(descriptionText).setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                    with(NotificationManagerCompat.from(this)) {
                        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return@runOnUiThread
                        notify(13, builder.build())
                    }
                }
            }
        }
    }
}
