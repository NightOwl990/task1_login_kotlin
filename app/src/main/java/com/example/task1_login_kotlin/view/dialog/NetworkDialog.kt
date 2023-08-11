package com.example.task1_login_kotlin.view.dialog

import android.app.Dialog
import android.content.Context
import com.example.namespace.R

class NetworkDialog(context: Context) : Dialog(context, R.style.Dialog_FullScreen) {
    init {
        setContentView(R.layout.dialog_network)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}