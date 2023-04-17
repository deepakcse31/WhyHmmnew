package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentMentorBinding
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.MentorsViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentorDetailsFragment : BaseFragment<FragmentMentorBinding,MentorsViewmodel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}