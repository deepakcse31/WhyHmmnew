package com.example.whyhmm.presentation.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentLoginBinding
import com.example.whyhmm.domain.utils.superNavigate
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.LoginViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginViewmodel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.apply {
            loginEditText.doOnTextChanged { text, start, before, count ->
                Log.e("Count","Count"+loginEditText.text?.length)
                if(loginEditText.text?.length==10)
                {
                    btnsubmit.background=ContextCompat.getDrawable(requireContext(),R.drawable.button_gradient)
                    btnsubmit.setTextColor(Color.WHITE)
                    btnsubmit.iconTint= ColorStateList.valueOf(Color.WHITE)
                }
                else{
                    btnsubmit.background=ContextCompat.getDrawable(requireContext(),R.drawable.button_inactive)
                    btnsubmit.setTextColor(ColorStateList.valueOf(Color.rgb(0,0,0)))
                    btnsubmit.iconTint= ColorStateList.valueOf(Color.rgb(0,0,0))
                }
            }
            btnsubmit.setOnClickListener {
                val action=LoginFragmentDirections.actionLoginToOtp()
                superNavigate(action)
            }
        }
    }

}