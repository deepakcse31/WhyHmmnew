package com.example.whyhmm.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whyhmm.databinding.MentorItemBinding
import com.example.whyhmm.databinding.TagsItemBinding

class TagsAdapter : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 3
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(val binding: TagsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = TagsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }
}