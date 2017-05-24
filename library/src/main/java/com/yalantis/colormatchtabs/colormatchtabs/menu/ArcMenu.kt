package com.yalantis.colormatchtabs.colormatchtabs.menu

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.PathInterpolatorCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.yalantis.colormatchtabs.colormatchtabs.*

/**
 * Created by anna on 19.05.17.
 */
class ArcMenu : FrameLayout {

    companion object{
        private const val MAX_ANGLE_FOR_MENU = 140.0
        private const val ANIMATION_DURATON = 200L
        private const val START_MENU_ANGLE = -20.0
    }

    private lateinit var fab: FloatingActionButton
    private lateinit var fabLayoutParams: LayoutParams
    private lateinit var backgroundPaint: Paint
    private var isMenuOpen = false
    internal var tabs: MutableList<ColorTab> = mutableListOf()
    private var currentRadius = 0f
    internal var menuToggleListener: MenuToggleListener? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setBackgroundColor(Color.TRANSPARENT)
        initCanvas()
        initFab()
    }

    private fun initCanvas() {
        setWillNotDraw(false)
        backgroundPaint = Paint()
        backgroundPaint.flags = Paint.ANTI_ALIAS_FLAG
    }

    private fun initFab() {
        fab = FloatingActionButton(context)
        fabLayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        fabLayoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        fab.layoutParams = fabLayoutParams
        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.plus))
        addView(fab)
        fab.setOnClickListener{ drawMenu() }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val heightLayout = (fab.height * 2) + calculateRadius()
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

    private fun drawMenu() {
        if(isMenuOpen) {
            menuToggleListener?.onCloseMenu()
            animateCloseMenu()
        } else {
            menuToggleListener?.onOpenMenu()
            animateOpenMenu()
        }
        isMenuOpen = !isMenuOpen
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(currentRadius != 0f) {
            layoutChildrenArc(canvas)
        }
    }

    private fun layoutChildrenArc(canvas: Canvas?) {
        val eachAngle = calculateSubMenuAngle()
        var angleForChild = START_MENU_ANGLE
        tabs.forEach {
            val childX = ((fab.x + (fab.width/2).toFloat()) - (currentRadius * Math.cos(Math.toRadians(angleForChild))).toFloat())
            val childY = ((fab.y+ (fab.height/2).toFloat()) + (currentRadius * Math.sin(Math.toRadians(angleForChild))).toFloat())
            backgroundPaint.color = it.selectedColor
            canvas?.drawCircle(childX, childY, fab.height / 2f, backgroundPaint)
            if(currentRadius == calculateRadius().toFloat() && isMenuOpen) {
                animateDrawableAppears(childX, childY, it, canvas)
            }
            angleForChild -= eachAngle
        }

    }

    private fun animateOpenMenu() {
        ValueAnimator.ofFloat(0f, calculateRadius().toFloat()).apply {
            duration = ANIMATION_DURATON
            interpolator = PathInterpolatorCompat.create(0.250f, 0.270f, 0.190f, 1.650f)
            addUpdateListener {
                currentRadius = animatedValue as Float
                invalidate()
            }
        }.start()
    }

    private fun animateDrawableAppears(childX: Float, childY: Float, tab: ColorTab, canvas: Canvas?) {
        val left = childX.toInt() - ((tab.icon?.intrinsicWidth ?: 0)/2)
        val top = childY.toInt() - ((tab.icon?.intrinsicHeight ?: 0)/2)
        tab.icon?.setColorFilter(getColor(R.color.mainBackgroundColor), PorterDuff.Mode.SRC_ATOP)
        tab.icon?.setBounds(left, top, left + (tab.icon?.intrinsicWidth ?: 0),  top + (tab.icon?.intrinsicHeight ?: 0))
        tab.icon?.draw(canvas)
    }

    private fun animateCloseMenu() {
        ValueAnimator.ofFloat(calculateRadius().toFloat(),  0f).apply {
            duration = ANIMATION_DURATON
            interpolator = FastOutLinearInInterpolator()
            addUpdateListener {
                currentRadius = animatedValue as Float
                invalidate()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }.start()
    }

    private fun calculateSubMenuAngle() = MAX_ANGLE_FOR_MENU / (tabs.count() - 1).toDouble()

    private fun calculateRadius() = width/3

}