package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentCourseBinding
import com.example.whyhmm.domain.adapter.CourseAdapter
import com.example.whyhmm.domain.adapter.HandicapedAdapter
import com.example.whyhmm.domain.adapter.MentorAdapter
import com.example.whyhmm.domain.adapter.PopularmarketingAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.domain.utils.superNavigate
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.CourseViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseFragment : BaseFragment<FragmentCourseBinding,CourseViewmodel>() {
    private var adaptercourse : CourseAdapter?=null
    private var adapterpopular : PopularmarketingAdapter?=null
    private var adaptermentor : MentorAdapter?=null
    private var adapterhandipicked : HandicapedAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        adaptercourse= CourseAdapter()
        adapterpopular= PopularmarketingAdapter()
        adaptermentor= MentorAdapter()
        adapterhandipicked= HandicapedAdapter()
        binding.apply {
            rvcourse.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            rvcourse.adapter=adaptercourse
            rvcourse.show()


            rvpopular.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            rvpopular.adapter=adapterpopular
            rvpopular.show()


            rvexperience.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            rvexperience.adapter=adaptermentor
            rvexperience.show()


            rvhandicaped.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            rvhandicaped.adapter=adapterhandipicked
            rvhandicaped.show()


        }
        binding.tvpopular.setOnClickListener {
            Log.e("Clicked","Clicked"+"Clicked")
            val action=HomeFramentDirections.actionHomefragmentToCoursesdetailsnewFragment()
            superNavigate(action)
        }
    }
}