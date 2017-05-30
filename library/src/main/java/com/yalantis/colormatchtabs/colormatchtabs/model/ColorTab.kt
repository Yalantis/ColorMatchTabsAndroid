package com.yalantis.colormatchtabs.colormatchtabs.model

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import com.yalantis.colormatchtabs.colormatchtabs.colortabs.ColorTabView


/**
 * Created by anna on 10.05.17.
 */
class ColorTab {

    var tabView: ColorTabView? = null

    var icon: Drawable? = null
        set(value) {
            field = value
            tabView?.updateView()
        }

    var text: CharSequence = ""
        set(value) {
            field = value
            tabView?.updateView()
        }


     var selectedColor: Int = Color.RED
        set(value) {
            field = value
            tabView?.updateView()
        }

    var position: Int = 0

    var isSelected: Boolean = false
        set(value) {
            field = value
            tabView?.updateView()
        }

}