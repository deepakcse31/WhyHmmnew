package com.example.whyhmm.domain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PreviewAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val frgList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()
    fun addfrg(frg: Fragment, title: String) {
        frgList.add(frg)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    override fun getItem(position: Int): Fragment {
        return frgList[position]
    }


    override fun getCount(): Int {
        return titleList.size
    }
}
