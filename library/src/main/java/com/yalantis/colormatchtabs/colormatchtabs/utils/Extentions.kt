package com.yalantis.colormatchtabs.colormatchtabs.utils

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

/**
 * Created by anna on 15.05.17.
 */
fun View.getDimenToFloat(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res).toFloat()

fun View.getDimen(@DimenRes res: Int) = context.resources.getDimensionPixelOffset(res)

fun View.getColor(@ColorRes res: Int) = ContextCompat.getColor(context, res)