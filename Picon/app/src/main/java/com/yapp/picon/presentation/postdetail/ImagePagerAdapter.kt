package com.yapp.picon.presentation.postdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImagePagerAdapter(
    fa: FragmentActivity,
    private val clickEvent: (String) -> Unit
): FragmentStateAdapter(fa) {
    private var items = listOf<String>()

    fun setItems(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return PostDetailImageSlideFragment(items[position]) { img -> clickEvent(img) }
    }

}