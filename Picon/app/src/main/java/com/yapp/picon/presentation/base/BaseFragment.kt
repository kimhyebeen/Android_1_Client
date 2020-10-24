package com.yapp.picon.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.yapp.picon.R

abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {
    protected lateinit var binding: T
    protected lateinit var rootView: View

    abstract val vm: VM
    abstract fun initBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        rootView = binding.root

        initBinding()

        return rootView
    }
}