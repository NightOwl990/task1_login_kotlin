package com.example.task1_login_kotlin.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.example.task1_login_kotlin.database.entities.Contact

class ContactListAdapter(private val listContacts: List<Contact>) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

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
        fun bind(contact: Contact) {
            tvName.text = contact.name
            tvPhone.text = contact.phoneNumber

            val first = contact.name.first().toString().uppercase()
            tvAlpha.text = first
            val color = generateColorFromLetter(first.first())
            tvAlpha.setTextColor(color)
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
