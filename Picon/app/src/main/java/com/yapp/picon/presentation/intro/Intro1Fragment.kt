package com.yapp.picon.presentation.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yapp.picon.R
import com.yapp.picon.databinding.Intro1FragmentBinding


class Intro1Fragment : Fragment() {

    private lateinit var binding: Intro1FragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.intro_1_fragment,
            container,
            false
        )
        return binding.root
    }

}