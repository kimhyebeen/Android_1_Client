package com.yapp.picon.presentation.nav

interface NavActivityObserver {
    fun finishNavActivity()
    fun getNavViewModel(): NavViewModel
}