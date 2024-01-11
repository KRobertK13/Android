package hu.bme.aut.android.mobweb.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.mobweb.AnalysisActivity
import hu.bme.aut.android.mobweb.R
import hu.bme.aut.android.mobweb.adapter.AnalysisPageAdapter
import hu.bme.aut.android.mobweb.databinding.FragmentAnalysisMainBinding


class AnalysisMainFragment : Fragment() {

    private lateinit var binding: FragmentAnalysisMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aa : AnalysisActivity
        aa = getActivity() as AnalysisActivity
        binding.vpAnalysis.adapter =AnalysisPageAdapter(this, aa)
    }
}