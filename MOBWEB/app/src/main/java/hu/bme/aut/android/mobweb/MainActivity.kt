package hu.bme.aut.android.mobweb

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.SaveInfo
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import com.google.androidbrowserhelper.trusted.NotificationUtils
import hu.bme.aut.android.mobweb.adapter.InExAdapter
import hu.bme.aut.android.mobweb.data.InExItem
import hu.bme.aut.android.mobweb.data.MDataBase
import hu.bme.aut.android.mobweb.data.TaLiItem
import hu.bme.aut.android.mobweb.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: MDataBase
    private val notificationPermissionRequestCode = 131313
    private lateinit var notificationUtils: NotificationUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = MDataBase.getDatabase(applicationContext)
        try {
            thread {
                try{
                    var taLiItemBuff: List<TaLiItem> = database.taLiItemDao().getAll()
                    if(taLiItemBuff.isNotEmpty()) {
                        runOnUiThread {
                            binding.etSaving.setText(taLiItemBuff[0].targetSavings.toString())
                            binding.etSpending.setText(taLiItemBuff[0].spendingLimit.toString())
                        }
                    }
                    else{
                        runOnUiThread {
                            binding.etSaving.setText("0")
                            binding.etSpending.setText("0")
                        }
                        SaveData()
                    }
                }
                catch(e: Exception) {
                    runOnUiThread {
                        binding.etSaving.setText("0")
                        binding.etSpending.setText("0")
                    }
                }
            }
        }
        catch (e:Exception){binding.etSaving.setText("0")
            binding.etSpending.setText("0")}
        binding.etSaving.setOnFocusChangeListener { _, hasFocus -> if(!hasFocus) SaveData() }
        binding.etSpending.setOnFocusChangeListener { _, hasFocus -> if(!hasFocus) SaveData() }
        binding.btnList.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            SaveData()
            startActivity(intent)
        }
        binding.btnAnal.setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            SaveData()
            startActivity(intent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "MOBWEB_CHANNEL_ID"
            val channelName = "MOBWEB_CHANNEL_NAME"
            createNotificationChannel(channelId, channelName)
        }
        if (checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), notificationPermissionRequestCode)
    }
    private fun getTaLiItem() = TaLiItem(
        targetSavings = (when (binding.etSaving.text.toString() == "") {true -> 0; false -> Integer.parseInt(binding.etSaving.text.toString())}),
        spendingLimit = (when (binding.etSpending.text.toString() == "") {true -> 0; false -> Integer.parseInt(binding.etSpending.text.toString())}),
    )
    private fun SaveData() {
        thread {
            try {
                for (item in database.taLiItemDao().getAll())
                    database.taLiItemDao().deleteItem(item)

            }
            catch(e: Exception) {

            }
            val insertId = database.taLiItemDao().insert(getTaLiItem())
        }

    }
    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}