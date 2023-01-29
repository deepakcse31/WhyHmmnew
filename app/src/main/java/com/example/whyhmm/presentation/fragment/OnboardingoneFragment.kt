package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentOnboardingoneBinding
import com.example.whyhmm.domain.utils.Circleload
import com.example.whyhmm.domain.utils.appendtext
import com.example.whyhmm.domain.utils.superNavigate
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.OnboardingOneViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingoneFragment : BaseFragment<FragmentOnboardingoneBinding,OnboardingOneViewmodel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.apply {
            tvheadlinedetails.text=""
            tvheadlinedetails.appendtext("For The ",R.color.black)
            tvheadlinedetails.appendtext("Curious Learner ",R.color.redcolor)
            tvheadlinedetails.appendtext("& The\n", android.R.color.black)
            tvheadlinedetails.appendtext("ambitious dreamers",R.color.secondarycolor)
            tvheadlinedetails.appendtext(". Choose an \n upskilling & excel in your \n career!", android.R.color.black)

           submitBtn.setOnClickListener {
               val action=OnboardingoneFragmentDirections.actionOnboardingoneToOnboardingtwo()
               superNavigate(action)
           }
        }
    }
}