package com.yalantis.colormatchtabs.colortabs

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.yalantis.colormatchtabs.colormatchtabs.menu.MenuToggleListener
import com.yalantis.colormatchtabs.colormatchtabs.adapter.ColorTabAdapter
import com.yalantis.colormatchtabs.colormatchtabs.listeners.ColorTabLayoutOnPageChangeListener
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnArcMenuListener
import com.yalantis.colormatchtabs.colormatchtabs.listeners.OnColorTabSelectedListener
import com.yalantis.colormatchtabs.colormatchtabs.model.ColorTab
import com.yalantis.colormatchtabs.colortabs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(binding.root)

        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorGreen))
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val colorsArray = resources.getStringArray(R.array.colors)
        val iconsArray = resources.obtainTypedArray(R.array.icons)
        val textsArray = resources.getStringArray(R.array.texts)

        colorsArray.forEachIndexed { index, color ->
            val tabName = textsArray[index]
            val selectedColor = Color.parseColor(color)
            val icon = iconsArray.getDrawable(index)
            binding.colorMatchTabLayout.addTab(
                ColorTabAdapter.createColorTab(
                    binding.colorMatchTabLayout,
                    tabName,
                    selectedColor,
                    icon!!
                )
            )
        }

        with(binding.viewPager) {
            adapter = ColorTabsAdapter(supportFragmentManager, binding.colorMatchTabLayout.count())
            addOnPageChangeListener(ColorTabLayoutOnPageChangeListener(binding.colorMatchTabLayout))
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorGreen))
            background.alpha = 128
        }

        binding.colorMatchTabLayout.addArcMenu(binding.arcMenu)
        binding.colorMatchTabLayout.addOnColorTabSelectedListener(object : OnColorTabSelectedListener {
            override fun onSelectedTab(tab: ColorTab?) {
                binding.viewPager.currentItem = tab?.position ?: 0
                binding.viewPager.setBackgroundColor(
                    tab?.selectedColor ?: ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorPrimary
                    )
                )
                binding.viewPager.background.alpha = 128
                binding.toolbar.setTitleTextColor(
                    tab?.selectedColor ?: ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorPrimary
                    )
                )
            }

            override fun onUnselectedTab(tab: ColorTab?) {
                Log.e("Unselected ", "tab")
            }
        })
        binding.arcMenu.addMenuToggleListener(object : MenuToggleListener {
            override fun onOpenMenu() {
                binding.viewUnderMenu.animateView(true)
            }

            override fun onCloseMenu() {
                binding.viewUnderMenu.animateView(false)
            }
        })
        binding.arcMenu.addOnClickListener(object : OnArcMenuListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Tab " + position.toString() + " is clicked",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


}
