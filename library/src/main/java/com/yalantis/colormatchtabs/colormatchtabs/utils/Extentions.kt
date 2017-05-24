package com.yalantis.colormatchtabs.colormatchtabs.utils

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * Created by anna on 15.05.17.
 */
fun View.getDimenToFloat(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res).toFloat()

fun View.getDimen(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res)

fun View.getColor(@ColorRes res: Int) = ContextCompat.getColor(context, res)