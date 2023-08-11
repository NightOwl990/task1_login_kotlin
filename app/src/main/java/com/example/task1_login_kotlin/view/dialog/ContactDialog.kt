package com.example.task1_login_kotlin.view.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.namespace.R
import com.example.task1_login_kotlin.App
import com.example.task1_login_kotlin.database.entities.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactDialog(context: Context, event: View.OnClickListener) : Dialog(context, R.style.Dialog_FullScreen) {
    private lateinit var btnAdd: TextView
    private lateinit var btnCancel: TextView
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private var event: View.OnClickListener
    init {
        this.event = event
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
            event.onClick(it)
            addContactIntoRoomDB()
            dismiss()
        }

        btnCancel.setOnClickListener{
            it.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in))
            dismiss()
        }
    }

    fun addContactIntoRoomDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            if (name.isEmpty()){
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(App.instance, "Name not null", Toast.LENGTH_SHORT).show()
                }
            } else if (phone.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(App.instance, "Phone not null", Toast.LENGTH_SHORT).show()
                }
            }
            val contact = Contact(name, phone)
            App.instance.getDb().contactDao().insertContact(contact)
            Log.i("Add", "ID: ${contact.id}, Name: ${contact.name}, Phone: ${contact.phoneNumber}")
        }
    }
}