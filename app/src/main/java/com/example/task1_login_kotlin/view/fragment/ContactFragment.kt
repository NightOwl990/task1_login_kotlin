package com.example.task1_login_kotlin.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.databinding.FragmentContactBinding
import com.example.task1_login_kotlin.App
import com.example.task1_login_kotlin.database.entities.Contact
import com.example.task1_login_kotlin.view.adapter.ContactsAdapter
import com.example.task1_login_kotlin.view.interfaces.TextChangeAdapter
import com.example.task1_login_kotlin.viewmodel.CommonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.Normalizer

class ContactFragment : BaseFragment<FragmentContactBinding, CommonViewModel>() {
    companion object {
        val TAG: String = ContactFragment::class.java.name
    }

    private var filteredContacts: List<Contact> = emptyList()
    private var mlist = ArrayList<Contact>()
    var listBasket: ArrayList<Contact> = arrayListOf()

    override fun initViewModel(): Class<CommonViewModel> = CommonViewModel::class.java

    override fun initViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false)


    override fun initViews() {
        bottomCallBack.showAddContact()
        checkPermissionAndLoadData()
        showAndHideWhenScrollView()
        searchContact()
    }

    private fun searchContact() {
        binding.edtSearchContact.addTextChangedListener(object : TextChangeAdapter {
            override fun afterTextChanged(s: Editable) { performSearch(s.toString())}
        })
    }

    private fun performSearch(query: String) {
        val cleanQueryName = query.lowercase().removeDiacritics().replace(" ", "")
        val cleanQueryPhone = query.lowercase().removeDiacritics().replace("-", "")
        filteredContacts = mlist.filter { contact ->
            val cleanName = contact.name.lowercase().removeDiacritics().replace(" ", "")
            val cleanPhoneNumber = contact.phoneNumber.removeDiacritics().replace("-", "")
            cleanName.contains(cleanQueryName) || cleanPhoneNumber.contains(cleanQueryPhone)
        }
        updateUI(filteredContacts)
    }

    private fun String.removeDiacritics(): String {
        val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
        return normalized.replace("\\p{M}".toRegex(), "")
    }

    private fun updateUI(updatedContacts: List<Contact>) {
        val groupedContacts = updatedContacts.sortedBy { it.name }.groupBy { it.name[0] }
        val contactsAdapter = ContactsAdapter(groupedContacts)
        binding.rcvContact.adapter = contactsAdapter
    }

    private fun showAndHideWhenScrollView() {
        binding.rcvContact.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 20) {
                    mCallBack.hideBottomBar() // Scroll down
                } else if (dy < -20) {
                    mCallBack.showBottomBar() // Scroll up
                }
            }
        })
    }

    private fun checkPermissionAndLoadData() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS), 999)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val listA = async { listBasket = App.instance.getDb().contactDao().getAllContact() as ArrayList<Contact> }
                listA.await()
                Log.i("DB", "$listBasket")
                if (listBasket.size > 0) mlist = listBasket else getContactAndSaveIntoRoomDB()
                setDataForRecyclerView()
            }
        }
    }

    @SuppressLint("Range")
    private fun getContactAndSaveIntoRoomDB() {
        val uri: Uri = ContactsContract.Contacts.CONTENT_URI
        val sort: String = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "ASC"
        val cursor: Cursor? = mContext.contentResolver.query(uri, null, null, null, sort)
        if ((cursor?.count ?: 0) > 0) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                    val columnName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val name: String = if (columnName != -1 && !cursor.isNull(columnName))
                    { cursor.getString(columnName) } else continue

                    val uriPhone: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    val selection: String = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                    val phoneCursor: Cursor? = mContext.contentResolver.query(uriPhone, null, selection, arrayOf(id), null)
                    if (phoneCursor != null && phoneCursor.moveToNext()) {

                        val columnPhone = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val phone: String = if (columnPhone != -1 && !phoneCursor.isNull(columnPhone))
                        { phoneCursor.getString(columnPhone) } else continue
                        phoneCursor.close()

                        CoroutineScope(Dispatchers.IO).launch {
                            App.instance.getDb().contactDao().insertContact(Contact(name, editPhoneNumber(phone)))
                            mlist = App.instance.getDb().contactDao().getAllContact() as ArrayList<Contact>
                            Log.i("DB", "$mlist")
                        }
                    }
                }
            }
            cursor?.close()
        }
    }

    private fun setDataForRecyclerView() {
        CoroutineScope(Dispatchers.Main).launch {
            val groupedContacts = mlist.sortedBy { it.name }.groupBy { it.name[0] }
            val layoutManager = LinearLayoutManager(mContext)
            val contactsAdapter = ContactsAdapter(groupedContacts)
            binding.rcvContact.layoutManager = layoutManager
            binding.rcvContact.adapter = contactsAdapter
        }
    }

    private fun editPhoneNumber(phoneNumber: String): String {
        val cleanedChar = phoneNumber.replace("-", "")
        val cleanedSpace = cleanedChar.replace(" ", "")
        val cleanedRegion = cleanedSpace.replace("+84", "0")
        if (cleanedRegion.length == 10) {
            return "${cleanedRegion.substring(0, 4)}-${cleanedRegion.substring(4, 7)}-${cleanedRegion.substring(7)}"
        }
        return cleanedRegion
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 999 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContactAndSaveIntoRoomDB()
            return
        }
        Toast.makeText(mContext, "Please allow access to contacts", Toast.LENGTH_SHORT).show()
    }
}

