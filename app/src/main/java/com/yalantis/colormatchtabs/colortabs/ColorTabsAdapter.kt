package com.yalantis.colormatchtabs.colortabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by anna on 10.05.17.
 */
class ColorTabsAdapter(fragmentManager: FragmentManager, amountTabs: Int) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val amountTabs = amountTabs

    override fun getItem(position: Int): Fragment = ListItemsFragment.newInstance()

    override fun getCount(): Int = amountTabs

}