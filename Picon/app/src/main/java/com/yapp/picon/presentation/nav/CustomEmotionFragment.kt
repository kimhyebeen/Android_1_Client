package com.yapp.picon.presentation.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yapp.picon.R
import kotlinx.android.synthetic.main.custom_emotion_item.view.*
import kotlinx.android.synthetic.main.nav_custom_emotion_fragment.view.*

class CustomEmotionFragment: Fragment() {
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.nav_custom_emotion_fragment, container, false)

        return rootView
    }
}