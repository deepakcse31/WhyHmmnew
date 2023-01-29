package com.example.whyhmm.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentWhereBinding
import com.example.whyhmm.domain.utils.superNavigate
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.WhereViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhereFragment : BaseFragment<FragmentWhereBinding,WhereViewmodel>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        binding.apply {
            val activestar = ContextCompat.getDrawable(requireContext(), R.drawable.activestar)
            val inactivestart = ContextCompat.getDrawable(requireContext(), R.drawable.inactive_star)

            ratingone.setOnClickListener {
                ratingone.background(R.drawable.active_rating_background)
                ratingone.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)
                ratingone.setTextColor(Color.WHITE)
                ratingtwo.background(R.drawable.inactive_rating_background)
                ratingtwo.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)
                ratingthree.background(R.drawable.inactive_rating_background)

                ratingthree.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)

                ratingfour.background(R.drawable.inactive_rating_background)
                ratingfour.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)
                ratingfive.background(R.drawable.inactive_rating_background)
                ratingfive.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)


                ratingtwo.setTextColor(Color.parseColor("#999999"))
                ratingthree.setTextColor(Color.parseColor("#999999"))
                ratingfour.setTextColor(Color.parseColor("#999999"))
                ratingfive.setTextColor(Color.parseColor("#999999"))
            }

            ratingtwo.setOnClickListener {

                ratingone.background(R.drawable.active_rating_background)
                ratingone.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingtwo.background(R.drawable.active_rating_background)
                ratingtwo.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingthree.background(R.drawable.inactive_rating_background)
                ratingthree.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)

                ratingfour.background(R.drawable.inactive_rating_background)
                ratingfour.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)
                ratingfive.background(R.drawable.inactive_rating_background)
                ratingfive.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)

                ratingone.setTextColor(Color.WHITE)
                ratingtwo.setTextColor(Color.WHITE)
                ratingthree.setTextColor(Color.parseColor("#999999"))
                ratingfour.setTextColor(Color.parseColor("#999999"))
                ratingfive.setTextColor(Color.parseColor("#999999"))
            }

            ratingthree.setOnClickListener {

                ratingone.background(R.drawable.active_rating_background)
                ratingone.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingtwo.background(R.drawable.active_rating_background)
                ratingtwo.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingthree.background(R.drawable.active_rating_background)
                ratingthree.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingfour.background(R.drawable.inactive_rating_background)
                ratingfour.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)
                ratingfive.background(R.drawable.inactive_rating_background)
                ratingfive.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)

                ratingone.setTextColor(Color.WHITE)
                ratingtwo.setTextColor(Color.WHITE)
                ratingthree.setTextColor(Color.WHITE)
                ratingfour.setTextColor(Color.parseColor("#999999"))
                ratingfive.setTextColor(Color.parseColor("#999999"))

            }

            ratingfour.setOnClickListener {

                ratingone.background(R.drawable.active_rating_background)
                ratingone.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingtwo.background(R.drawable.active_rating_background)
                ratingtwo.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingthree.background(R.drawable.active_rating_background)
                ratingthree.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingfour.background(R.drawable.active_rating_background)
                ratingfour.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)
                ratingfive.background(R.drawable.inactive_rating_background)
                ratingfive.setCompoundDrawablesWithIntrinsicBounds(null, null,inactivestart, null)


                ratingone.setTextColor(Color.WHITE)
                ratingtwo.setTextColor(Color.WHITE)
                ratingthree.setTextColor(Color.WHITE)
                ratingfour.setTextColor(Color.WHITE)
                ratingfive.setTextColor(Color.parseColor("#999999"))

            }



            ratingfive.setOnClickListener {

                ratingone.background(R.drawable.active_rating_background)
                ratingone.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingtwo.background(R.drawable.active_rating_background)
                ratingtwo.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingthree.background(R.drawable.active_rating_background)
                ratingthree.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingfour.background(R.drawable.active_rating_background)
                ratingfour.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingfive.background(R.drawable.active_rating_background)
                ratingfive.setCompoundDrawablesWithIntrinsicBounds(null, null,activestar, null)

                ratingone.setTextColor(Color.WHITE)
                ratingtwo.setTextColor(Color.WHITE)
                ratingthree.setTextColor(Color.WHITE)
                ratingfour.setTextColor(Color.WHITE)
                ratingfive.setTextColor(Color.WHITE)

            }
            btnsubmit.setOnClickListener {
                val action=WhereFragmentDirections.actionWherefragmentToAboutwhyfragment()
                superNavigate(action)
            }
        }


    }
}