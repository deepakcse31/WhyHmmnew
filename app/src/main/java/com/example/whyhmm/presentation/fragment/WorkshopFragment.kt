package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentWorkshopBinding
import com.example.whyhmm.domain.adapter.SeriesAdapter
import com.example.whyhmm.domain.adapter.WorkshopAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.WorkshopViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkshopFragment : BaseFragment<FragmentWorkshopBinding,WorkshopViewmodel>() {

    private var adapterworkshop : WorkshopAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        adapterworkshop= WorkshopAdapter()
        binding.apply {
            rvworkshop.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvworkshop.adapter=adapterworkshop
            rvworkshop.show()
        }
    }
}