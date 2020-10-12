package com.yapp.picon.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {
    protected lateinit var binding: T

    abstract val vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

    }
}