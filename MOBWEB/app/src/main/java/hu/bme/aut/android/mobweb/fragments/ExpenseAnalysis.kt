package hu.bme.aut.android.mobweb.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate.*
import hu.bme.aut.android.mobweb.AnalysisActivity
import hu.bme.aut.android.mobweb.data.InExItem
import hu.bme.aut.android.mobweb.data.MDataBase
import hu.bme.aut.android.mobweb.databinding.FragmentExpenseAnalysisBinding
import kotlin.concurrent.thread


class ExpenseAnalysis(aa: AnalysisActivity) : Fragment() {
    private lateinit var binding: FragmentExpenseAnalysisBinding
    private var database: MDataBase = aa.getDb()
    private var AA = aa;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExpenseAnalysisBinding.inflate(layoutInflater)
        thread {
            var osszeg: Int = 0
            var max: Int = 0
            val w = mutableListOf<InExItem>()
            var b = mutableListOf<InExItem>()
            var s = mutableListOf<InExItem>()
            var e = mutableListOf<InExItem>()
            var o = mutableListOf<InExItem>()
            for(item in database.inExItemDao().getAll()){
                osszeg += when(!item.sign){true-> item.amount; false-> 0}
                if(!item.sign){
                    when (item.category) {
                        InExItem.Category.WORK -> w.add(item)
                        InExItem.Category.PURCHASE -> b.add(item)
                        InExItem.Category.SALE -> s.add(item)
                        InExItem.Category.ENTERTAINMENT -> e.add(item)
                        InExItem.Category.OVERHEAD -> o.add(item)
                    }
                }
            }
            max = database.taLiItemDao().getAll()[0].spendingLimit
            AA.runOnUiThread {
                binding.ratio.text = "$osszeg/$max"
                if(osszeg > max) binding.ratio.setTextColor(Color.parseColor("#FF0000"))
                else if(osszeg < max) binding.ratio.setTextColor(Color.parseColor("#00AA00"))

                setupPie(w, b, s, e, o)
                if (max != 0) {
                    var percentage = (osszeg.toDouble() / max) * 100
                    var v: Int = percentage.toInt()
                    binding.textViewProgress.text = "$v%"
                    if (osszeg < 0) osszeg = 0
                    if (osszeg > max) osszeg = max
                    percentage = (osszeg.toDouble() / max) * 100
                    v = percentage.toInt()
                    binding.progressBar.progress = v

                } else {
                    binding.progressBar.progress = 0
                    binding.textViewProgress.text = "0%"
                }
                binding.stat.text = "Work: ${w.size}\nPurchase: ${b.size}\nSale: ${s.size}\nEntertainment: ${e.size}\nOverhead: ${o.size}"
            }
        }
        return binding.root
    }
    private fun setupPie(w: MutableList<InExItem>, b: MutableList<InExItem>, s: MutableList<InExItem>, e: MutableList<InExItem>, o: MutableList<InExItem>){
        val entries = mutableListOf<PieEntry>()
       if(b.isNotEmpty()) entries.add(PieEntry(summa(b).toFloat(), "PURCHASE"))
       if(s.isNotEmpty()) entries.add(PieEntry(summa(s).toFloat(), "SALE"))
       if(e.isNotEmpty()) entries.add(PieEntry(summa(e).toFloat(), "ENTERTAINMENT"))
       if(o.isNotEmpty()) entries.add(PieEntry(summa(o).toFloat(), "OVERHEAD"))
       if(w.isNotEmpty()) entries.add(PieEntry(summa(w).toFloat(), "WORK"))
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = MATERIAL_COLORS.toList()
        val data = PieData(dataSet)
        data.setValueTextSize(15.0f)
        binding.chartExpenses.data = data
        binding.chartExpenses.setEntryLabelTextSize(15.0F)
        binding.chartExpenses.setCenterTextSize(15.0F)
        binding.chartExpenses.description.textSize = 15.0F
        binding.chartExpenses.centerTextRadiusPercent = 30.0F
        binding.chartExpenses.description.text=""
        binding.chartExpenses.invalidate()
    }
    private fun summa(e: MutableList<InExItem>): Int {
        var v = 0
        for(item in e) v += item.amount
        return v
    }
}