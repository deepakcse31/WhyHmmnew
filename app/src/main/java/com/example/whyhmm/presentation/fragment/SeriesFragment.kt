package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentSeriesBinding
import com.example.whyhmm.domain.adapter.HandicapedAdapter
import com.example.whyhmm.domain.adapter.SeriesAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.SeriesViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : BaseFragment<FragmentSeriesBinding,SeriesViewmodel>() {

    private var adapterseries : SeriesAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        adapterseries= SeriesAdapter()
        binding.apply {
            rvseries.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvseries.adapter=adapterseries
            rvseries.show()
        }
    }
}