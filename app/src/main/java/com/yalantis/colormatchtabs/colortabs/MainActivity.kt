package com.yalantis.colormatchtabs.colortabs

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.yalantis.colormatchtabs.colormatchtabs.ColorTab
import com.yalantis.colormatchtabs.colormatchtabs.ColorTabLayoutOnPageChangeListener
import com.yalantis.colormatchtabs.colormatchtabs.OnColorTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

        val firstTab = tabLayout.newTab()
        firstTab.text = getString(R.string.products)
        firstTab.selectedColor = Color.GREEN
        firstTab.icon = resources.getDrawable(R.drawable.ic_accessibility_black_24dp)

        val secondTab = tabLayout.newTab()
        secondTab.text = getString(R.string.venues)
        secondTab.selectedColor = Color.GREEN
        secondTab.icon = resources.getDrawable(R.drawable.ic_accessibility_black_24dp)

        val thirdTab = tabLayout.newTab()
        thirdTab.text = getString(R.string.reviews)
        thirdTab.selectedColor = Color.GREEN
        thirdTab.icon = resources.getDrawable(R.drawable.ic_accessibility_black_24dp)

        val fourthTab = tabLayout.newTab()
        fourthTab.text = getString(R.string.friends)
        fourthTab.selectedColor = Color.GREEN
        fourthTab.icon = resources.getDrawable(R.drawable.ic_accessibility_black_24dp)

        val fiveTab = tabLayout.newTab()
        fiveTab.text = getString(R.string.friends)
        fiveTab.icon = resources.getDrawable(R.drawable.ic_accessibility_black_24dp)

        val sixTab = tabLayout.newTab()
        sixTab.text = getString(R.string.friends)
        sixTab.icon = resources.getDrawable(R.drawable.ic_accessibility_black_24dp)

        tabLayout.addTab(firstTab)
        tabLayout.addTab(secondTab)
        tabLayout.addTab(thirdTab)
        tabLayout.addTab(fourthTab)
        tabLayout.addTab(fiveTab)
        tabLayout.addTab(sixTab)

        pager.adapter = ColorTabsAdapter(supportFragmentManager, tabLayout.count())
        pager.addOnPageChangeListener(ColorTabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnColorTabSelectedListener(object : OnColorTabSelectedListener {
            override fun onSelectedTab(tab: ColorTab?) {
                pager.currentItem = tab?.position ?: 0
            }
        })
    }
}
