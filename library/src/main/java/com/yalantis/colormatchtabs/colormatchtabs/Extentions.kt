package com.yalantis.colormatchtabs.colormatchtabs

import android.support.annotation.DimenRes
import android.view.View

/**
 * Created by anna on 15.05.17.
 */
fun View.getDimenToFloat(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res).toFloat()

fun View.getDimen(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res)