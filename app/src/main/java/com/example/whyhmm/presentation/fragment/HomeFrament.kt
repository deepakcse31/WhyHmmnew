package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentHomeFramentBinding
import com.example.whyhmm.domain.adapter.AutoScrollAdapter
import com.example.whyhmm.domain.adapter.PreviewAdapter
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFrament : BaseFragment<FragmentHomeFramentBinding,HomeViewmodel>() {
    var adapterauto : AutoScrollAdapter?=null
    var dummyaar : ArrayList<String>?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        val adapt = PreviewAdapter(childFragmentManager)
        val courseFragment = CourseFragment()
        val seriesFragment=SeriesFragment()
        val workshopFragment=WorkshopFragment()
        val mentorFragment=MentorFragment()
        adapt.addfrg(courseFragment,"Course")
        adapt.addfrg(seriesFragment,"Series")
        adapt.addfrg(workshopFragment,"Workshops")
        adapt.addfrg(mentorFragment,"Mentors")
        dummyaar?.add("")
        dummyaar?.add("")
        adapterauto= AutoScrollAdapter(dummyaar)
      //  binding.postsTabs.post { binding.postsTabs.setupWithViewPager(binding.postTabsViewPager) }
        // adapter= dummyaar?.let { AutoScrollAdapter(it) }
        binding.apply {
            cardslidder.adapter=adapterauto
            postsTabs.setupWithViewPager(binding.postTabsViewPager)
            postTabsViewPager.adapter=adapt
            postTabsViewPager.offscreenPageLimit=4
        }
    }
}