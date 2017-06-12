package com.yalantis.colormatchtabs.colormatchtabs.listeners

import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab

/**
 * Created by anna on 11.05.17.
 */
/**
 * Callback interface invoked when a tab's selection state changes.
 */
interface OnColorTabSelectedListener {

    /**
     * Called when a tab enters the selected state.
     *
     * @param tab The tab that was selected
     */
    fun onSelectedTab(tab: ColorTab?)

    /**
     * Called when a tab exits the selected state.
     *
     * @param tab The tab that was unselected
     */
    fun onUnselectedTab(tab: ColorTab?)

}