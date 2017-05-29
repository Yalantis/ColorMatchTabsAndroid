package com.yalantis.colormatchtabs.colortabs

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.yalantis.colormatchtabs.colortabs.ListItemsFragment

/**
 * Created by anna on 10.05.17.
 */
class ColorTabsAdapter(fragmentManager: FragmentManager, amountTabs: Int) : FragmentStatePagerAdapter(fragmentManager) {

    private val amountTabs = amountTabs

    override fun getItem(position: Int): Fragment = ListItemsFragment.newInstance()

    override fun getCount(): Int = amountTabs

}