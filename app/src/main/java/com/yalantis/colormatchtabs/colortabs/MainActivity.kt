package com.yalantis.colormatchtabs.colortabs

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.yalantis.colormatchtabs.colormatchtabs.adapter.ColorTabAdapter
import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab
import com.yalantis.colormatchtabs.colormatchtabs.listeners.ColorTabLayoutOnPageChangeListener
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnColorTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        val colorsArray = resources.getStringArray(R.array.colors)
        val iconsArray = resources.obtainTypedArray(R.array.icons)
        val textsArray = resources.getStringArray(R.array.texts)

        colorsArray.forEachIndexed { index, color ->
            val s = textsArray[index]
            val color1 = Color.parseColor(color)
            val drawable = iconsArray.getDrawable(index)
            tabLayout.addTab(ColorTabAdapter.createColorTab(tabLayout, s, color1, drawable))
        }

        pager.adapter = ColorTabsAdapter(supportFragmentManager, tabLayout.count())
        pager.addOnPageChangeListener(ColorTabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addArcMenu(arcMenu)
        tabLayout.addOnColorTabSelectedListener(object : OnColorTabSelectedListener {
            override fun onSelectedTab(tab: ColorTab?) {
                pager.currentItem = tab?.position ?: 0
            }
        })
    }

}
