package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentCoursesBinding
import com.example.whyhmm.domain.adapter.CourseAdapter
import com.example.whyhmm.domain.adapter.HomeMentorAdapter
import com.example.whyhmm.domain.adapter.MyCoursesAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.CoursesViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : BaseFragment<FragmentCoursesBinding,CoursesViewmodel>() {
    private var adaptercourses : MyCoursesAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        adaptercourses= MyCoursesAdapter()
        binding.apply {

            var gridLayoutManager=
                GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            //  gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvcourses.layoutManager= gridLayoutManager
            rvcourses.adapter=adaptercourses
            rvcourses.show()
        }
    }
}