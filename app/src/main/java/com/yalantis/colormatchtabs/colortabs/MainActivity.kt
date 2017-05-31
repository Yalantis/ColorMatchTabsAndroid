package com.yalantis.colormatchtabs.colortabs

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.facebook.drawee.backends.pipeline.Fresco
import com.yalantis.colormatchtabs.colormatchtabs.MenuToggleListener
import com.yalantis.colormatchtabs.colormatchtabs.adapter.ColorTabAdapter
import com.yalantis.colormatchtabs.colormatchtabs.listeners.ColorTabLayoutOnPageChangeListener
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnArcMenuListener
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnColorTabSelectedListener
import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)
        toolbar.toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.colorGreen))
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val colorsArray = resources.getStringArray(R.array.colors)
        val iconsArray = resources.obtainTypedArray(R.array.icons)
        val textsArray = resources.getStringArray(R.array.texts)

        colorsArray.forEachIndexed { index, color ->
            val tabName = textsArray[index]
            val selectedColor = Color.parseColor(color)
            val icon = iconsArray.getDrawable(index)
            colorMatchTabLayout.addTab(ColorTabAdapter.createColorTab(colorMatchTabLayout, tabName, selectedColor, icon))
        }

        viewPager.adapter = ColorTabsAdapter(supportFragmentManager, colorMatchTabLayout.count())
        viewPager.addOnPageChangeListener(ColorTabLayoutOnPageChangeListener(colorMatchTabLayout))
        //TODO think how change this ugly methods!!
        viewPager.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen))
        viewPager.background.alpha = 128
        colorMatchTabLayout.addArcMenu(arcMenu)
        colorMatchTabLayout.addOnColorTabSelectedListener(object : OnColorTabSelectedListener {
            override fun onSelectedTab(tab: ColorTab?) {
                viewPager.currentItem = tab?.position ?: 0
                viewPager.setBackgroundColor(tab?.selectedColor ?: ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
                viewPager.background.alpha = 128
                toolbar.toolbarTitle.setTextColor(tab?.selectedColor ?: ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
            }

            override fun onUnselectedTab(tab: ColorTab?) {Log.e("Unselected ", "tab")}
        })
        arcMenu.addMenuToggleListener(object : MenuToggleListener {
            override fun onOpenMenu() {
                viewUnderMenu.animateView(true)
            }

            override fun onCloseMenu() {
                viewUnderMenu.animateView(false)
            }
        })
        arcMenu.addOnClickListener(object : OnArcMenuListener {
            override fun onClick(position: Int) {
                Toast.makeText(this@MainActivity, "Tab " + position.toString() + " is clicked", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


}
