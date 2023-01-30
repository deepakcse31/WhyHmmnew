package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentMentorBinding
import com.example.whyhmm.domain.adapter.HomeMentorAdapter
import com.example.whyhmm.domain.adapter.MentorAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.MentorsViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorFragment : BaseFragment<FragmentMentorBinding,MentorsViewmodel>() {
    private var adaptermntor : HomeMentorAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        adaptermntor= HomeMentorAdapter()
        binding.apply {

            var gridLayoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
          //  gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvmentor.layoutManager= gridLayoutManager
            rvmentor.adapter=adaptermntor
            rvmentor.show()
        }
    }

}