package com.yalantis.colormatchtabs.colormatchtabs

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.view.animation.PathInterpolatorCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout

/**
 * Created by anna on 11.05.17.
 */
class SlidingTabStrip : LinearLayout {

    var parentLayout: ColorMatchTabLayout? = null
    private lateinit var backgroundPaint: Paint
    private var backgroundCanvas: Canvas? = null

    internal var childView: ColorTabView? = null
    internal var isAnimate: Boolean = false
    private var animateLeftX = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL
        setWillNotDraw(false)
        initCanvas()
    }

    private fun initCanvas() {
        backgroundPaint = Paint()
        backgroundPaint.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        (0..childCount - 1).forEach {
            val child = getChildAt(it) as ColorTabView
            backgroundCanvas = canvas
            if (child.tab?.isSelected ?: false) {
                backgroundPaint.color = child.tab?.selectedColor ?: Color.WHITE
            }
            if (child.tab?.isSelected ?: false && isAnimate) {
                childView = child
                animateRectangle(child, canvas)
            } else if (child.tab?.isSelected ?: false && !isAnimate) {
                drawBackgroundTab(child, canvas)
            }
        }
    }

    private fun drawBackgroundTab(child: ColorTabView, canvas: Canvas?) {
        if (child.tab?.position == 0) {
            canvas?.drawRect(RectF(child.x, 0f, (child.x + child.width.toFloat() - getDimen(R.dimen.radius)), child.height.toFloat()), backgroundPaint)
        } else if (child.tab?.position == parentLayout?.count()?.minus(1)) {
            canvas?.drawRect(RectF((child.x + getDimen(R.dimen.radius)), 0f, (child.x + child.width.toFloat()), child.height.toFloat()), backgroundPaint)
        }
        val left = if (child.tab?.position == 0) child.x.minus(getDimen(R.dimen.radius)) else child.x
        val right = if (child.tab?.position == parentLayout?.count()?.minus(1)) child.x.plus(child.width).plus(getDimen(R.dimen.radius)) else child.x.plus(child.width)
        val rectangle = RectF(left, 0f, right, child.height.toFloat())
        canvas?.drawRoundRect(rectangle, getDimen(R.dimen.radius), getDimen(R.dimen.radius), backgroundPaint)
    }

    internal fun animateDrawTab(child: ColorTabView?) {
        ValueAnimator.ofFloat(parentLayout?.previousSelectedTab?.x ?: 0f, child?.x ?: 0f).apply {
            duration = 200
            interpolator = PathInterpolatorCompat.create(0.175f, 0.885f, 0.360f, 1.200f)
            addUpdateListener {
                animateLeftX = animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    child?.clickedTabView = null
                    isAnimate = false
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    isAnimate = true
                }
            })
        }.start()
    }

    private fun animateRectangle(child: ColorTabView, canvas: Canvas?) {
        var leftX = animateLeftX
        val left = if (child.tab?.position == 0) leftX.minus(getDimen(R.dimen.radius)) else leftX
        val right = if (child.tab?.position == parentLayout?.count()?.minus(1)) leftX.plus(child.width).plus(getDimen(R.dimen.radius)) else leftX.plus(child.width)
        val rectangle = RectF(left, 0f, right, child.height.toFloat())
        canvas?.drawRoundRect(rectangle, getDimen(R.dimen.radius), getDimen(R.dimen.radius), backgroundPaint)
    }

}