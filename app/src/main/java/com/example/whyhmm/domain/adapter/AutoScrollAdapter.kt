package com.example.whyhmm.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whyhmm.databinding.LayAutoScrollItemBinding
import com.github.islamkhsh.CardSliderAdapter

class AutoScrollAdapter(private var banner: ArrayList<String>?)  : CardSliderAdapter<AutoScrollAdapter.MovieViewHolder>() {

    override fun getItemCount() = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view =  LayAutoScrollItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return MovieViewHolder(view)
    }

    override fun bindVH(holder: MovieViewHolder, position: Int) {

    }

    inner class MovieViewHolder(val binding: LayAutoScrollItemBinding): RecyclerView.ViewHolder(binding.root)

}