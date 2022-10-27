package com.mobidal.pharmacynamoune.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class MainFragmentAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    var mFragmentList: MutableList<Fragment> = ArrayList()
    var mTitleList: MutableList<String> = ArrayList()
    var mIconResourceIdList: MutableList<Int> = ArrayList()
    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    fun getIconResourceId(position: Int): Int {
        return mIconResourceIdList[position]
    }

    fun addFragment(fragment: Fragment, title: String, iconResourceId: Int) {
        mFragmentList.add(fragment)
        mTitleList.add(title)
        mIconResourceIdList.add(iconResourceId)
    }

    fun getName(position: Int): String {
        return mTitleList[position]
    }
}