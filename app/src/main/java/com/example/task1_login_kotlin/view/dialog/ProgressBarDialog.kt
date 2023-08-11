package com.example.task1_login_kotlin.view.dialog

import android.app.Dialog
import android.content.Context
import com.example.namespace.R

class ProgressBarDialog(context: Context) : Dialog(context, R.style.Dialog_FullScreen) {
    init {
        setContentView(R.layout.dialog_progressbar)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}