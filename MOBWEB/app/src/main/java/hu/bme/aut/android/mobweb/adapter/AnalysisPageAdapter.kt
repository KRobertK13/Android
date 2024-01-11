package hu.bme.aut.android.mobweb.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.mobweb.AnalysisActivity
import hu.bme.aut.android.mobweb.data.MDataBase
import hu.bme.aut.android.mobweb.fragments.ExpenseAnalysis
import hu.bme.aut.android.mobweb.fragments.SavingAnalysis

class AnalysisPageAdapter(fragment: Fragment, aa: AnalysisActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = NUM_PAGES
    private var analysisActivity: AnalysisActivity = aa

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> ExpenseAnalysis(analysisActivity)
        1 -> SavingAnalysis(analysisActivity)
        else -> ExpenseAnalysis(analysisActivity)
    }

    companion object{
        const val NUM_PAGES = 2
    }
}