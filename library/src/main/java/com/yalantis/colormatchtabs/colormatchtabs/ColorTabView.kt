package com.yalantis.colormatchtabs.colormatchtabs

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by anna on 10.05.17.
 */
class ColorTabView : LinearLayout, View.OnClickListener {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initColorTabView()
    }

    internal var tab: ColorTab? = null
        set(value) {
            field = value
            updateView()
        }

    internal var parentLayout: ColorMatchTabLayout? = null
    private lateinit var textView: TextView
    internal lateinit var iconView: ImageView

    fun initColorTabView() {
        gravity = Gravity.CENTER
        orientation = HORIZONTAL
        isClickable = true
        setBackgroundColor(Color.TRANSPARENT)
        initViews()
        this.setOnClickListener(this@ColorTabView)
    }

    private fun initViews() {
        iconView = ImageView(context)
        iconView.setBackgroundColor(Color.TRANSPARENT)
        addView(iconView)
        textView = TextView(context)
        addView(textView)
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        // This view masquerades as an action bar tab.
        event.className = ActionBar.Tab::class.java.name
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        // This view masquerades as an action bar tab.
        info.className = ActionBar.Tab::class.java.name
    }

    override fun onMeasure(origWidthMeasureSpec: Int, origHeightMeasureSpec: Int) {
        val specWidthSize = MeasureSpec.getSize(origWidthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(origWidthMeasureSpec)
        val maxWidth = parentLayout?.tabMaxWidth

        val widthMeasureSpec: Int
        val heightMeasureSpec = origHeightMeasureSpec  - getDimen(R.dimen.tab_padding)
        if (maxWidth ?: 0 > 0 && (specWidthMode == MeasureSpec.UNSPECIFIED || specWidthSize > maxWidth ?: 0)) {
            // If we have a max width and a given spec which is either unspecified or
            // larger than the max width, update the width spec using the same mode
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(if (tab?.isSelected ?: false) getDimen(R.dimen.tab_max_width) else maxWidth ?: 0, MeasureSpec.EXACTLY)
        } else {
            // Else, use the original width spec
            widthMeasureSpec = origWidthMeasureSpec
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        iconView.setPadding(getDimen(R.dimen.normal_margin), 0, getDimen(R.dimen.normal_margin), getDimen(R.dimen.tab_padding))
        textView.setPadding(0, 0, getDimen(R.dimen.normal_margin), getDimen(R.dimen.tab_padding))
        if(clickedTabView != null) {
            parentLayout?.tabStrip?.animateDrawTab(clickedTabView)
        }
    }

    fun updateView() {
        val colorTab = tab
        if (tab?.isSelected ?: false) {
            textView.visibility = View.VISIBLE
            textView.text = colorTab?.text
            textView.setTextColor(Color.WHITE)
            textView.requestLayout()
        } else {
            textView.visibility = View.GONE
        }
        if (colorTab?.icon != null) {
            Log.e("ICON", iconView.x.toString())
            Log.e("ICON", iconView.y.toString())
            iconView.setImageDrawable(colorTab.icon)
            reColorDrawable(colorTab.isSelected)
            iconView.requestLayout()
        }

        requestLayout()
    }

    internal var clickedTabView: ColorTabView? = null

    override fun onClick(v: View?) {
        if(!(parentLayout?.tabStrip?.isAnimate ?: false)) {
            val clickedTabView = v as ColorTabView?
            parentLayout?.select(clickedTabView?.tab)
            this.clickedTabView = clickedTabView
        }
    }

    internal fun reColorDrawable(isSelected: Boolean) {
        if (isSelected) {
            iconView.colorFilter = null
            iconView.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP)
        } else {
            iconView.setColorFilter(tab?.selectedColor ?: 0, PorterDuff.Mode.SRC_ATOP)
        }
    }

}