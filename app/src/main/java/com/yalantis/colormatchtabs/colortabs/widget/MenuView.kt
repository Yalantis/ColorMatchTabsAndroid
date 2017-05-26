package com.yalantis.colormatchtabs.colortabs.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import com.yalantis.colormatchtabs.colortabs.R

/**
 * Created by anna on 25.05.17.
 */
class MenuView : LinearLayout {

    private var radius = 300f
    private val backgroundPaint: Paint = Paint()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setWillNotDraw(false)
        backgroundPaint.flags = Paint.ANTI_ALIAS_FLAG
        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorWhite)
    }

    fun animateBackground() {
        ValueAnimator.ofFloat(0f, height.toFloat()).apply {
            duration = 200
            addUpdateListener {
                radius = animatedValue as Float
                invalidate()
            }
        }.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(80f, 80f, radius, backgroundPaint)
    }
}