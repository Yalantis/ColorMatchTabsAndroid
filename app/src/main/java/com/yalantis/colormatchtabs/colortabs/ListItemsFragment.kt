package com.yalantis.colormatchtabs.colortabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yalantis.colormatchtabs.colortabs.databinding.FragmentListItemsBinding
import com.yalantis.colormatchtabs.colortabs.model.Menu

/**
 * Created by anna on 09.05.17.
 */
class ListItemsFragment : Fragment() {

    private lateinit var binding: FragmentListItemsBinding

    private val listOfPictures by lazy { listOf(BASE_SCHEME + R.drawable.eat, BASE_SCHEME + R.drawable.coffe) }
    private val listOfDishName by lazy { listOf(requireContext().getString(R.string.caesar), requireContext().getString(R.string.latte)) }
    private val listOfRestaurant by lazy { listOf(requireContext().getString(R.string.cafe), requireContext().getString(R.string.stareducks)) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.menuList.layoutManager = LinearLayoutManager(context)
        binding.menuList.adapter = ListItemAdapter()
        (binding.menuList.adapter as ListItemAdapter).items = createListItems()
    }

    private fun createListItems(): List<Menu> {
        val list = mutableListOf<Menu>()
        for (i in 0..5) {
            val index = i.rem(2)
            list.add(Menu(listOfPictures[index], listOfDishName[index], listOfRestaurant[index], requireContext().getString(R.string.caesar_review)))
        }
        return list
    }

    companion object {
        private const val BASE_SCHEME = "res:///"
        fun newInstance() = ListItemsFragment()
    }
}