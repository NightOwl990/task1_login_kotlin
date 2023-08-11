package com.example.task1_login_kotlin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.example.task1_login_kotlin.database.entities.Contact

class ContactsAdapter(private val contacts: Map<Char, List<Contact>>) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val char = contacts.keys.elementAt(position)
        val contactList = contacts[char] ?: emptyList()
        holder.bind(char, contactList)
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(char: Char, contactList: List<Contact>) {
            itemView.findViewById<TextView>(R.id.tv_char).text = char.toString()
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.tv_group_contact)

            val contactListAdapter = ContactListAdapter(contactList)
            recyclerView.adapter = contactListAdapter
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
        }
    }
}
