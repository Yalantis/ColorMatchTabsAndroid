package com.yalantis.colormatchtabs.colormatchtabs.model

import android.graphics.Color
import android.graphics.drawable.Drawable
import com.yalantis.colormatchtabs.colormatchtabs.colortabs.ColorTabView


/**
 * Created by anna on 10.05.17.
 */
class ColorTab {

    companion object{
        private const val INVALID_POSITION = -1
    }

    var tabView: ColorTabView? = null

    /**
     * Sets and rReturn the icon associated with this tab.
     *
     * @return The tab's icon
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            tabView?.updateView()
        }

    /**
     * Sets/return the text displayed on this tab.
     *
     * @return The tab's text
     */
    var text: CharSequence = ""

    /**
     * Sets/return the selected color of this tab. If color is not set return Color.GREEN
     *
     * @return The tab's selected color
     */
    var selectedColor: Int = Color.GREEN

    /**
     * Sets/return the current position of this tab in the action bar.
     *
     * @return Current position, or {@link #INVALID_POSITION} if this tab is not currently in
     * the action bar.
     */
    var position: Int = INVALID_POSITION

    /**
     * Returns true if this tab is currently selected.
     */
    var isSelected: Boolean = false
        set(value) {
            field = value
            tabView?.updateView()
        }

}