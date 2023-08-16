package com.example.task1_login_kotlin.view.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.namespace.R
import com.example.task1_login_kotlin.App
import com.example.task1_login_kotlin.database.entities.Contact
import com.example.task1_login_kotlin.view.interfaces.OnDeleteContactCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListAdapter(private var listContacts: MutableList<Contact>) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {
    private val TAG: String = ContactListAdapter::class.java.name
    private var viewBinderHelper: ViewBinderHelper = ViewBinderHelper()
    private lateinit var callBack: OnDeleteContactCallBack
    fun setCallBack(callBack: OnDeleteContactCallBack) {
        this.callBack = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_list, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = listContacts[position]
        holder.bind(contact)

    }

    override fun getItemCount(): Int = listContacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val tvAlpha: TextView = itemView.findViewById(R.id.tv_alpha)
        private val swipeRevealLayout: SwipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout)
        private val layoutDelete: LinearLayout = itemView.findViewById(R.id.layout_delete)
        private val foregroundLayout: LinearLayout = itemView.findViewById(R.id.foreground_layout)

        fun bind(contact: Contact) {
            viewBinderHelper.bind(swipeRevealLayout, contact.id.toString())
            layoutDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    App.instance.getDb().contactDao().deleteContact(contact)
                    listContacts = App.instance.getDb().contactDao().getAllContact().toMutableList()
                    callBack.updateRecyclerUI(listContacts)
                    Log.i(TAG, "Size list: $listContacts")
                }
            }

            tvName.text = contact.name
            tvPhone.text = contact.phoneNumber
            val first = contact.name.first().toString().uppercase()
            tvAlpha.text = first
            val color = generateColorFromLetter(first.first())
            tvAlpha.setTextColor(color)

            foregroundLayout.setOnClickListener{
                callBack.callPhone(tvPhone.text.toString())
            }
        }
    }

}

private fun generateColorFromLetter(letter: Char): Int {
    val uniqueValue = letter.code % 26          // Chuyển mã ASCII thành giá trị từ 0 đến 25
    val red = ((uniqueValue * 123) % 256)       // Tạo giá trị red từ 0 đến 255
    val green = ((uniqueValue * 43) % 256)      // Tạo giá trị green từ 0 đến 255
    val blue = ((uniqueValue * 431) % 256)      // Tạo giá trị blue từ 0 đến 255
    return Color.rgb(red, green, blue)
}
