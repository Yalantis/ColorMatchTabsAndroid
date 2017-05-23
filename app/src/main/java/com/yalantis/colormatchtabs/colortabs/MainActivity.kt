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
        firstTab.selectedColor = Color.parseColor("#79BC32")
        firstTab.icon = resources.getDrawable(R.drawable.reviews_selected)

        val secondTab = tabLayout.newTab()
        secondTab.text = getString(R.string.venues)
        secondTab.selectedColor = Color.parseColor("#3DB4F8")
        secondTab.icon = resources.getDrawable(R.drawable.goods)

        val thirdTab = tabLayout.newTab()
        thirdTab.text = getString(R.string.reviews)
        thirdTab.selectedColor = Color.parseColor("#F89900")
        thirdTab.icon = resources.getDrawable(R.drawable.venues)

        val fourthTab = tabLayout.newTab()
        fourthTab.text = getString(R.string.friends)
        fourthTab.selectedColor = Color.parseColor("#EB8487")
        fourthTab.icon = resources.getDrawable(R.drawable.users)

        tabLayout.addTab(firstTab)
        tabLayout.addTab(secondTab)
        tabLayout.addTab(thirdTab)
        tabLayout.addTab(fourthTab)

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
