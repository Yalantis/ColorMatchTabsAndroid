package com.yalantis.colormatchtabs.colortabs.demo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by anna on 10.05.17.
 */
class ColorTabsAdapter(fragmentManager: FragmentManager, amountTabs: Int) : FragmentStatePagerAdapter(fragmentManager) {

    private val amountTabs = amountTabs

    override fun getItem(position: Int): Fragment = ListItemsFragment.newInstance()

    override fun getCount(): Int = amountTabs
}