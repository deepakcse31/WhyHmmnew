package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentOnboardingtwoBinding
import com.example.whyhmm.domain.utils.appendtext
import com.example.whyhmm.domain.utils.superNavigate
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.OnboardingTwoViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingtwoFragment :BaseFragment<FragmentOnboardingtwoBinding,OnboardingTwoViewmodel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.apply {
            tvheadlinedetails.text=""
            tvheadlinedetails.appendtext("Connect With The ",R.color.black)
            tvheadlinedetails.appendtext("Mentors. \n",R.color.secondarycolor)
            tvheadlinedetails.appendtext("Discuss Your ", android.R.color.black)
            tvheadlinedetails.appendtext("Ideas, Questions\n",R.color.redcolor)
            tvheadlinedetails.appendtext("Share Your Experiances With\n", android.R.color.black)
            tvheadlinedetails.appendtext("WhyHmm ",R.color.secondarycolor)
            tvheadlinedetails.appendtext("Everyday.", android.R.color.black)
            submitBtn.setOnClickListener {
                val action=OnboardingtwoFragmentDirections.actionOnboardingtwoToOnboardingthree()
                superNavigate(action)
            }
        }
    }

}