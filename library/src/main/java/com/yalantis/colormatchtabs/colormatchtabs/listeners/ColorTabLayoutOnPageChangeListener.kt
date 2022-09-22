package com.yalantis.colormatchtabs.colormatchtabs.listeners

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING
import com.yalantis.colormatchtabs.colormatchtabs.colortabs.ColorMatchTabLayout
import java.lang.ref.WeakReference

/**
 * Created by anna on 11.05.17.
 */
class ColorTabLayoutOnPageChangeListener(colorTabLayout: ColorMatchTabLayout) : ViewPager.OnPageChangeListener {

    private var previousScrollState: Int = 0
    private var scrollState: Int = 0

    private val tabLayoutReference: WeakReference<ColorMatchTabLayout> = WeakReference(colorTabLayout)

    override fun onPageScrollStateChanged(state: Int) {
        previousScrollState = scrollState
        scrollState = state
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val tabLayout = tabLayoutReference.get()
        val updateIndicator = !(scrollState == SCROLL_STATE_SETTLING && previousScrollState == SCROLL_STATE_IDLE)
        tabLayout?.setScrollPosition(position, positionOffset, updateIndicator)
    }

    override fun onPageSelected(position: Int) {
        val tabLayout = tabLayoutReference.get()
        if (tabLayout?.tabStripLayout?.isAnimate?.not() ?: true) {
            tabLayout?.select(tabLayout.getTabAt(position))
            tabLayout?.getSelectedTabView()?.clickedTabView = tabLayout?.getSelectedTabView()
        }
    }

}