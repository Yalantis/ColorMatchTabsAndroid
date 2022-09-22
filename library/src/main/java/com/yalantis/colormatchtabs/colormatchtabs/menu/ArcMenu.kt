package com.yalantis.colormatchtabs.colormatchtabs.menu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yalantis.colormatchtabs.colormatchtabs.Constant.Companion.ANIMATION_DURATION
import com.yalantis.colormatchtabs.colormatchtabs.R
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnArcMenuListener
import com.yalantis.colormatchtabs.colormatchtabs.model.CircleSubMenu
import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab
import com.yalantis.colormatchtabs.colormatchtabs.utils.getColor
import com.yalantis.colormatchtabs.colormatchtabs.utils.getDimen

/**
 * Created by anna on 19.05.17.
 */
class ArcMenu : FrameLayout {

    companion object {
        private const val MAX_ANGLE_FOR_MENU = 140.0
        private const val START_MENU_ANGLE = -20.0
        private const val CONTROL_X1 = 0.250f
        private const val CONTROL_Y1 = 0.270f
        private const val CONTROL_X2 = 0.190f
        private const val CONTROL_Y2 = 1.650f
    }

    private lateinit var fab: FloatingActionButton
    private lateinit var fabLayoutParams: LayoutParams
    private lateinit var backgroundPaint: Paint
    private var isMenuOpen = false
    internal var listOfTabs: MutableList<ColorTab> = mutableListOf()
    private var circleSubMenu: MutableList<CircleSubMenu> = mutableListOf()
    private var currentRadius = 0f
    internal var menuToggleListener: MenuToggleListener? = null
    private var userMenuToggleListener: MenuToggleListener? = null
    private var arcMenuListener: OnArcMenuListener? = null
    private var isMenuAnimating: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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
        fabLayoutParams = LayoutParams(getDimen(R.dimen.fab_size), getDimen(R.dimen.fab_size))
        fabLayoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        fab.useCompatPadding = true
        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.plus))
        super.addView(fab, 0, fabLayoutParams)
        fab.setOnClickListener {
            if (!isMenuAnimating) {
                drawMenu()
            }
        }
    }

    fun addMenuToggleListener(toggleListener: MenuToggleListener) {
        this.userMenuToggleListener = toggleListener
    }

    fun addOnClickListener(arcMenuClick: OnArcMenuListener) {
        this.arcMenuListener = arcMenuClick
    }

    override fun onMeasure(widthMeasureSpec: Int, originHeightMeasureSpec: Int) {
        var heightMeasureSpec = originHeightMeasureSpec
        val heightLayout = (fab.height * 2) + calculateRadius()
        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST -> heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    Math.min(heightLayout, MeasureSpec.getSize(heightMeasureSpec)), MeasureSpec.EXACTLY)
            MeasureSpec.UNSPECIFIED -> heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightLayout, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    var isLayoutParamNeedToSet = true
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(isLayoutParamNeedToSet) {
                fab.layoutParams = fabLayoutParams
                isLayoutParamNeedToSet = false
            }
        }
    }

    private fun drawMenu() {
        if (isMenuOpen) {
            userMenuToggleListener?.onCloseMenu()
            menuToggleListener?.onCloseMenu()
            animateCloseMenu()
            fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_backward))
        } else {
            userMenuToggleListener?.onOpenMenu()
            menuToggleListener?.onOpenMenu()
            animateOpenMenu()
            fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_forward))
        }
        isMenuOpen = !isMenuOpen
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (currentRadius != 0f) {
            layoutChildrenArc(canvas)
        }
    }

    private fun layoutChildrenArc(canvas: Canvas) {
        val eachAngle = calculateSubMenuAngle()
        var angleForChild = START_MENU_ANGLE
        listOfTabs.forEach {
            val childX = ((fab.x + (fab.width / 2).toFloat()) - (currentRadius * Math.cos(Math.toRadians(angleForChild))).toFloat())
            val childY = ((fab.y + (fab.height / 2).toFloat()) + (currentRadius * Math.sin(Math.toRadians(angleForChild))).toFloat())
            backgroundPaint.color = it.selectedColor
            canvas.drawCircle(childX, childY, calculateCircleSize(), backgroundPaint)
            if (currentRadius >= (calculateRadius().toFloat() - (calculateRadius().toFloat() / 3)) && isMenuOpen) {
                animateDrawableAppears(childX, childY, it, canvas)
            }
            angleForChild -= eachAngle
        }
    }

    private fun calculateCircleSize() = (fab.height - fab.paddingTop) / 2f

    private fun animateOpenMenu() {
        ValueAnimator.ofFloat(0f, calculateRadius().toFloat()).apply {
            duration = ANIMATION_DURATION
            interpolator = PathInterpolatorCompat.create(CONTROL_X1, CONTROL_Y1, CONTROL_X2, CONTROL_Y2)
            addUpdateListener {
                currentRadius = animatedValue as Float
                invalidate()
            }
            addListener(animationListener)
        }.start()
    }

    private fun animateDrawableAppears(childX: Float, childY: Float, tab: ColorTab, canvas: Canvas) {
        val left = childX.toInt() - ((tab.icon?.intrinsicWidth ?: 0) / 2)
        val top = childY.toInt() - ((tab.icon?.intrinsicHeight ?: 0) / 2)
        tab.icon?.setColorFilter(getColor(R.color.mainBackgroundColor), PorterDuff.Mode.SRC_ATOP)
        tab.icon?.setBounds(left, top, left + (tab.icon?.intrinsicWidth ?: 0), top + (tab.icon?.intrinsicHeight ?: 0))
        tab.icon?.draw(canvas)
    }

    private fun animateCloseMenu() {
        ValueAnimator.ofFloat(calculateRadius().toFloat(), 0f).apply {
            duration = ANIMATION_DURATION
            interpolator = FastOutLinearInInterpolator()
            addUpdateListener {
                currentRadius = animatedValue as Float
                invalidate()
            }
            addListener(animationListener)
        }.start()
    }

    private fun calculateSubMenuAngle() = MAX_ANGLE_FOR_MENU / (listOfTabs.count() - 1).toDouble()

    private fun calculateRadius() = width / 3

    private fun calculateSubMenu() {
        val eachAngle = MAX_ANGLE_FOR_MENU / (listOfTabs.count() - 1).toDouble()
        var angleForChild = START_MENU_ANGLE
        listOfTabs.forEach {
            val childX = ((fab.x + (fab.width / 2).toFloat()) - (calculateRadius() * Math.cos(Math.toRadians(angleForChild))).toFloat())
            val childY = ((fab.y + (fab.height / 2).toFloat()) + (calculateRadius() * Math.sin(Math.toRadians(angleForChild))).toFloat())
            circleSubMenu.add(CircleSubMenu(childX, childY, calculateCircleSize()))
            angleForChild -= eachAngle
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (circleSubMenu.isEmpty()) {
            calculateSubMenu()
        }
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isMenuOpen) {
                    click(event.x, event.y)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun click(x: Float, y: Float) {
        circleSubMenu.forEachIndexed { index, circleSubMenu ->
            if ((circleSubMenu.x - circleSubMenu.radius) <= x && x <= (circleSubMenu.x + circleSubMenu.radius)) {
                if ((circleSubMenu.y - circleSubMenu.radius) <= y && y <= (circleSubMenu.y + circleSubMenu.radius)) {
                    arcMenuListener?.onClick(index)
                }
            }
        }
    }

    private val animationListener = object : AnimatorListenerAdapter() {

        override fun onAnimationEnd(animation: Animator) {
            isMenuAnimating = false
        }

        override fun onAnimationStart(animation: Animator) {
            isMenuAnimating = true
        }
    }

}