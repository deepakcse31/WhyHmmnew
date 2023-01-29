package com.example.whyhmm.presentation.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentNameBinding
import com.example.whyhmm.databinding.FragmentWhereBinding
import com.example.whyhmm.domain.utils.superNavigate
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.NameViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : BaseFragment<FragmentNameBinding,NameViewmodel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.apply {
            edtname.doOnTextChanged { text, start, before, count ->
                if(edtname.text?.length ==0)
                {
                    btnsubmit.background=ContextCompat.getDrawable(requireContext(),R.drawable.button_inactive)
                    btnsubmit.setTextColor(ColorStateList.valueOf(Color.rgb(0,0,0)))
                    btnsubmit.iconTint= ColorStateList.valueOf(Color.rgb(0,0,0))
                }
                else{
                    btnsubmit.background=ContextCompat.getDrawable(requireContext(),R.drawable.button_gradient)
                    btnsubmit.setTextColor(Color.WHITE)
                    btnsubmit.iconTint= ColorStateList.valueOf(Color.WHITE)
                }
            }

                btnsubmit.setOnClickListener {
                    Log.e("Button_clicked","Button_clicked")
                    val action=NameFragmentDirections.actionNamefragmentToWherefragment()
                    superNavigate(action)
                }

        }
    }
}