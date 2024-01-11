package hu.bme.aut.android.mobweb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.mobweb.data.MDataBase
import hu.bme.aut.android.mobweb.databinding.ActivityAnalysisBinding

class AnalysisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalysisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalysisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
    fun getDb() = MDataBase.getDatabase(applicationContext)
}