package com.example.whyhmm.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentWalletBinding
import com.example.whyhmm.domain.adapter.MarketingEpisodeAdapter
import com.example.whyhmm.domain.adapter.TagsAdapter
import com.example.whyhmm.domain.adapter.TransactionAdapter
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.WalletViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding,WalletViewmodel>() {

    private var adaptertransaction : TransactionAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        adaptertransaction= TransactionAdapter()

        binding.apply {
            rvtransaction.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvtransaction.adapter=adaptertransaction
            rvtransaction.show()

        }
    }
}