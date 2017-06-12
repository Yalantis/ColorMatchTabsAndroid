package com.yalantis.colormatchtabs.colortabs.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.PathInterpolatorCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.yalantis.colormatchtabs.colortabs.R

/**
 * Created by anna on 25.05.17.
 */
class MenuView : LinearLayout {

    companion object {
        private const val ANIMATION_DURATION = 200L
        private const val CONTROL_X1 = 0.250f
        private const val CONTROL_Y1 = 0.270f
        private const val CONTROL_X2 = 0.190f
        private const val CONTROL_Y2 = 1.650f
    }

    private var radius = 0f
    private val backgroundPaint: Paint = Paint()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setWillNotDraw(false)
        backgroundPaint.flags = Paint.ANTI_ALIAS_FLAG
        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorWhite)
    }

    fun animateBackground(isMenuOpen: Boolean) {
        visibility = View.VISIBLE
        val start = if (isMenuOpen) 0f else (height.toFloat() * 2)
        val end = if (isMenuOpen) (height.toFloat() * 2) else 0f
        ValueAnimator.ofFloat(start, end).apply {
            duration = ANIMATION_DURATION
            interpolator = PathInterpolatorCompat.create(CONTROL_X1, CONTROL_Y1, CONTROL_X2, CONTROL_Y2)
            addUpdateListener {
                radius = animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    if (!isMenuOpen) {
                        getChildAt(0).visibility = View.GONE
                    } else {
                        getChildAt(0).visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (!isMenuOpen) {
                        visibility = View.GONE
                    }
                }
            })
        }.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle((width / 2).toFloat(), height.toFloat(), radius, backgroundPaint)
    }

}