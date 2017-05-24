package com.yalantis.colormatchtabs.colormatchtabs.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import com.yalantis.colormatchtabs.colormatchtabs.colortabs.ColorMatchTabLayout
import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab

/**
 * Created by anna on 24.05.17.
 */
class ColorTabAdapter {

    companion object {

        @JvmStatic
        fun createColorTab(tabLayout: ColorMatchTabLayout, text: String, color: Int, icon: Drawable): ColorTab {
            val colorTab = tabLayout.newTab()
            colorTab.text = text
            colorTab.selectedColor = color
            colorTab.icon = icon
            return colorTab
        }
    }
}