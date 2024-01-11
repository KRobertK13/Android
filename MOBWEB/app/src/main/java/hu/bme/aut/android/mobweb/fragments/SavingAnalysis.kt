package hu.bme.aut.android.mobweb.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.mobweb.AnalysisActivity
import hu.bme.aut.android.mobweb.R
import hu.bme.aut.android.mobweb.data.MDataBase
import hu.bme.aut.android.mobweb.databinding.FragmentSavingAnalysisBinding
import kotlin.concurrent.thread


class SavingAnalysis( aa: AnalysisActivity) : Fragment() {

    private lateinit var binding: FragmentSavingAnalysisBinding
    private var database: MDataBase = aa.getDb()
    private var AA = aa;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSavingAnalysisBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var osszeg: Int = 0
        var max: Int = 0
        thread {
            for(item in database.inExItemDao().getAll())
                osszeg += when(item.sign){true-> item.amount; false-> -item.amount}
            max = database.taLiItemDao().getAll()[0].targetSavings
            AA.runOnUiThread {
                binding.ratio.text = "$osszeg/$max"
                var percentage = (osszeg.toDouble() / max) * 100
                var v: Int = percentage.toInt()
                binding.textViewProgress.text = "$v%"
                if(osszeg < 0) binding.ratio.setTextColor(Color.parseColor("#FF0000"))
                else if(osszeg > 0) binding.ratio.setTextColor(Color.parseColor("#00AA00"))
                if (max != 0) {
                    if (osszeg < 0) osszeg = 0
                    if (osszeg > max) osszeg = max
                    percentage = (osszeg.toDouble() / max) * 100
                    v = percentage.toInt()
                    binding.progressBar.progress = v
                } else {
                    binding.progressBar.progress = 0
                    binding.textViewProgress.text = "0%"
                }
            }
        }
    }
}