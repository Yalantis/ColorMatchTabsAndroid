package com.yalantis.colormatchtabs.colormatchtabs.menu

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yalantis.colormatchtabs.colormatchtabs.R
import com.yalantis.colormatchtabs.colormatchtabs.getDimen

/**
 * Created by anna on 19.05.17.
 */
class ArcMenu : FrameLayout {

    private var fab: FloatingActionButton
    private var fabLayoutParams: LayoutParams

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setBackgroundColor(Color.TRANSPARENT)
        fab = FloatingActionButton(context)
        fabLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER)
        fab.layoutParams = fabLayoutParams
        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.plus))
        addView(fab)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val heightLayout = fab.height + (getDimen(R.dimen.padding_normal)*2)
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                Math.min(heightLayout, MeasureSpec.getSize(heightMeasureSpec)),
                MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        fabLayoutParams.bottomMargin = getDimen(R.dimen.margin_small)
        fab.layoutParams = fabLayoutParams
    }
}