package com.yalantis.colormatchtabs.colortabs

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout

/**
 * Created by anna on 10.05.17.
 */
class ColorMatchTabLayout : HorizontalScrollView {

    companion object {
        private const val INVALID_WIDTH = -1
        private const val DEFAULT_HEIGHT_WITH_TEXT_ICON = 48
        internal const val DEFAULT_HEIGHT = 48
        internal const val DEFAULT_WIDTH = 56
        private const val TAB_MAX_WIDTH = 132
    }

    private lateinit var tabStrip: SlidingTabStrip
    private var tabs: MutableList<ColorTab> = mutableListOf()
    private var tabSelectedListener: OnColorTabSelectedListener? = null
    internal var selectedTab: ColorTab? = null
    internal var tabMaxWidth = Integer.MAX_VALUE
    internal var padingMax = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initLayout(attrs, defStyleAttr)
    }

    private fun initLayout(attrs: AttributeSet?, defStyleAttr: Int) {
        isHorizontalScrollBarEnabled = false
        tabStrip = SlidingTabStrip(context)
        tabStrip.parentLayout = this
        super.addView(tabStrip, 0, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorMatchTabLayout)
        initViewTreeObserver(typedArray)
    }

    fun initViewTreeObserver(typedArray: TypedArray) {
        typedArray.getDimensionPixelSize(android.support.design.R.styleable.TabLayout_tabMinWidth,
                INVALID_WIDTH)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        // If we have a MeasureSpec which allows us to decide our height, try and use the default
        // height
        val idealHeight = (dpToPx(DEFAULT_HEIGHT) + paddingTop + paddingBottom)
        when (View.MeasureSpec.getMode(heightMeasureSpec)) {
            View.MeasureSpec.AT_MOST -> heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    Math.min(idealHeight, View.MeasureSpec.getSize(heightMeasureSpec)),
                    View.MeasureSpec.EXACTLY)
            View.MeasureSpec.UNSPECIFIED -> heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(idealHeight, View.MeasureSpec.EXACTLY)
        }

        val specWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.UNSPECIFIED) {
            // If we don't have an unspecified width spec, use the given size to calculate
            // the max tab width
            val systemService = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val probable = (systemService.defaultDisplay.width - dpToPx(TAB_MAX_WIDTH)) / (tabs.size - 1)
            Log.e("WIDTH", systemService.defaultDisplay.width.toString())
            Log.e("MAX_TAB", dpToPx(TAB_MAX_WIDTH).toString())
            Log.e("SIZE", (tabs.size - 1).toString())
            Log.e("PROBABLE", probable.toString())
            tabMaxWidth = if (probable < dpToPx(DEFAULT_WIDTH)) dpToPx(DEFAULT_WIDTH) else probable
        }

        // Now super measure itself using the (possibly) modified height spec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    private fun getDefaultHeight(): Int {
        var hasIconAndText = false
        var i = 0
        val count = tabs.size
        while (i < count) {
            val tab = tabs.get(i)
            if (tab?.icon != null && !TextUtils.isEmpty(tab?.text)) {
                hasIconAndText = true
                break
            }
            i++
        }
        return if (hasIconAndText) DEFAULT_HEIGHT_WITH_TEXT_ICON else DEFAULT_HEIGHT
    }

    fun addTab(tab: ColorTab) {
        tab.isSelected = tabs.isEmpty()
        if (tab.isSelected) {
            selectedTab = tab
        }
        addColorTabView(tab)
    }

    private fun addColorTabView(tab: ColorTab) {
        configureTab(tab, tabs.size)
        tabStrip.addView(tab.tabView, tab.position, createLayoutParamsForTabs())
    }

    fun newTab(): ColorTab {
        val colorTab = ColorTab()
        colorTab.tabView = createTabView(colorTab)
        return colorTab
    }

    fun createTabView(tab: ColorTab): ColorTabView {
        val colorTabView = ColorTabView(context)
        colorTabView.tab = tab
        colorTabView.parentLayout = this
        return colorTabView
    }

    private fun configureTab(tab: ColorTab, position: Int) {
        tab.position = position
        tabs.add(position, tab)

        val count = tabs.size
        for (i in position + 1..count - 1) {
            tabs.get(i).position = i
        }
    }

    private fun createLayoutParamsForTabs(): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        updateTabViewLayoutParams(lp)
        return lp
    }

    private fun updateTabViewLayoutParams(lp: LinearLayout.LayoutParams) {
//        if (mMode == MODE_FIXED && mTabGravity == GRAVITY_FILL) {
        lp.width = 0
        lp.weight = 1f
//        } else {
//            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT
//            lp.weight = 0f
//        }
    }

    fun count(): Int = tabs.size

    fun getTabAt(index: Int): ColorTab? {
        if (index < 0 || index >= count()) {
            return null
        } else {
            return tabs.get(index)
        }
    }

    internal fun setScrollPosition(position: Int, positionOffset: Float, updateSelectedText: Boolean) {
        val roundedPosition = Math.round(position + positionOffset)
        if (roundedPosition < 0 || roundedPosition >= tabStrip.getChildCount()) {
            return
        }

        // Update the 'selected state' view as we scroll, if enabled
        if (updateSelectedText) {
            setSelectedTabView(roundedPosition)
        }
    }

    fun addOnColorTabSelectedListener(tabSelectedListener: OnColorTabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener
    }

    private fun setSelectedTabView(position: Int) {
        val tabCount = tabStrip.getChildCount()
        if (position < tabCount) {
            for (i in 0..tabCount - 1) {
                val child = tabStrip.getChildAt(i)
                child.setSelected(i == position)
            }
        }
    }

    internal fun select(selectTab: ColorTab?) {
        if (selectTab == selectedTab) {

        } else {
            selectedTab?.isSelected = false
            selectedTab = selectTab
            selectedTab?.isSelected = true
        }
        tabSelectedListener?.onSelectedTab(selectTab)
    }

    private fun dpToPx(dps: Int): Int {
        return Math.round(resources.displayMetrics.density * dps)
    }
}