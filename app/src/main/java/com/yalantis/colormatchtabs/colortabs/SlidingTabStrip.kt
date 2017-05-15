package com.yalantis.colormatchtabs.colortabs

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout

/**
 * Created by anna on 11.05.17.
 */
class SlidingTabStrip : LinearLayout {

    var parentLayout: ColorMatchTabLayout? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL
    }
}