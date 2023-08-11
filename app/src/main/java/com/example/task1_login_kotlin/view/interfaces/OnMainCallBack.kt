package com.example.task1_login_kotlin.view.interfaces

interface OnMainCallBack : OnBottomCallBack {
    fun showFragment(tag: String, data: Any?, isBack: Boolean)

}