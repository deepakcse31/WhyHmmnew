package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentProfileBinding
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.ProfileViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileViewmodel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)
    }
}