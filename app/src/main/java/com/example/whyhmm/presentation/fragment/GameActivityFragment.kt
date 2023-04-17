package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentGameActivityBinding
import com.example.whyhmm.domain.adapter.GameActivityAdapter
import com.example.whyhmm.domain.adapter.MyCoursesAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.GameActivityViemodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivityFragment : BaseFragment<FragmentGameActivityBinding,GameActivityViemodel>() {
    private var adaptergames : GameActivityAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.activity_color)
        adaptergames= GameActivityAdapter()
        binding.apply {
       var gridLayoutManager=
                GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            rvposts.layoutManager= gridLayoutManager
            rvposts.adapter=adaptergames
            rvposts.show()
        }
    }
}