package com.yalantis.colormatchtabs.colortabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yalantis.colormatchtabs.colortabs.model.Menu
import kotlinx.android.synthetic.main.fragment_list_items.*

/**
 * Created by anna on 09.05.17.
 */
class ListItemsFragment : Fragment() {

    companion object {
        private const val BASE_SCHEME = "res:///"
        fun newInstance() = ListItemsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_list_items, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuList.layoutManager = LinearLayoutManager(context)
        menuList.adapter = ListItemAdapter()
        (menuList.adapter as ListItemAdapter).items = createListItems()
    }

    private fun createListItems(): List<Menu> {
        val list = mutableListOf<Menu>()
        list.add(Menu(BASE_SCHEME + R.drawable.eat, context.getString(R.string.caesar), context.getString(R.string.cafe), context.getString(R.string.caesar_review)))
        list.add(Menu(BASE_SCHEME + R.drawable.coffe, context.getString(R.string.latte), context.getString(R.string.stareducks), context.getString(R.string.caesar_review)))
        list.add(Menu(BASE_SCHEME + R.drawable.eat, context.getString(R.string.caesar), context.getString(R.string.cafe), context.getString(R.string.caesar_review)))
        list.add(Menu(BASE_SCHEME + R.drawable.coffe, context.getString(R.string.latte), context.getString(R.string.stareducks), context.getString(R.string.caesar_review)))
        list.add(Menu(BASE_SCHEME + R.drawable.eat, context.getString(R.string.caesar), context.getString(R.string.cafe), context.getString(R.string.caesar_review)))
        list.add(Menu(BASE_SCHEME + R.drawable.coffe, context.getString(R.string.latte), context.getString(R.string.stareducks), context.getString(R.string.caesar_review)))
        return list
    }

}