package com.example.task1_login_kotlin.view.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.example.namespace.R
import com.example.task1_login_kotlin.view.interfaces.OnContactCallBack

class ContactDialog(context: Context, var callBack: OnContactCallBack) : Dialog(context, R.style.Dialog_FullScreen) {
    private lateinit var btnAdd: TextView
    private lateinit var btnCancel: TextView
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText

    init {
        setContentView(R.layout.dialog_contact)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        initViews()
    }

    private fun initViews() {
        btnAdd = findViewById(R.id.btn_add)
        btnCancel = findViewById(R.id.btn_cacel)
        edtName = findViewById(R.id.edt_name)
        edtPhone = findViewById(R.id.edt_phone)

        btnAdd.setOnClickListener{
            it.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in))
            callBack.addContact(edtName.text.toString(), edtPhone.text.toString())
            dismiss()
        }

        btnCancel.setOnClickListener{
            it.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in))
            dismiss()
        }
    }
}