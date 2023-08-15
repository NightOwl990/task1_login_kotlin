package com.example.task1_login_kotlin.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.example.task1_login_kotlin.database.entities.Contact
import com.example.task1_login_kotlin.view.interfaces.OnDeleteContactCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsAdapter(var context: Context, private var contacts: MutableMap<Char, List<Contact>>) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(), OnDeleteContactCallBack{
    private lateinit var callBack: OnDeleteContactCallBack
    fun setCallBack(callBack: OnDeleteContactCallBack){
        this.callBack = callBack
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Contact>) {
        contacts.clear()
        val groupedContacts = newData.sortedBy { it.name }.groupBy { it.name[0] }
        contacts = groupedContacts as MutableMap<Char, List<Contact>>
        CoroutineScope(Dispatchers.Main).launch {
            notifyDataSetChanged()
        }
    }

    private lateinit var contactListAdapter: ContactListAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val char = contacts.keys.elementAt(position)
        val contactList = contacts[char] ?: emptyList()
        holder.bind(char, contactList as MutableList<Contact>)
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(char: Char, contactList: MutableList<Contact>) {
            itemView.findViewById<TextView>(R.id.tv_char).text = char.toString()
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.rv_group_contact)

            contactListAdapter = ContactListAdapter(contactList)
            contactListAdapter.setCallBack(this@ContactsAdapter)
            recyclerView.adapter = contactListAdapter
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)

        }
    }
    override fun updateRecyclerUI(list: List<Contact>) {
        callBack.updateRecyclerUI(list)
    }


}
