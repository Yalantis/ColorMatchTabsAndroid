package com.yalantis.colormatchtabs.colortabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yalantis.colormatchtabs.colortabs.R

/**
 * Created by anna on 09.05.17.
 */
class ListItemsFragment : Fragment() {

    companion object {
        fun newInstance() = ListItemsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_list_items, container, false)

    }

}