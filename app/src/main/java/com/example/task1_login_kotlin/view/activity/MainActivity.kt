package com.example.task1_login_kotlin.view.activity

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.namespace.R
import com.example.namespace.databinding.ActivityMainBinding
import com.example.task1_login_kotlin.App
import com.example.task1_login_kotlin.database.entities.Contact
import com.example.task1_login_kotlin.utils.CommonUtils
import com.example.task1_login_kotlin.view.dialog.ContactDialog
import com.example.task1_login_kotlin.view.fragment.ContactFragment
import com.example.task1_login_kotlin.view.fragment.HomeFragment
import com.example.task1_login_kotlin.view.fragment.LoginFragment
import com.example.task1_login_kotlin.view.fragment.SplashFragment
import com.example.task1_login_kotlin.view.interfaces.OnBottomCallBack
import com.example.task1_login_kotlin.view.interfaces.OnContactCallBack
import com.example.task1_login_kotlin.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), OnBottomCallBack, OnContactCallBack {
    private var contactDialog: ContactDialog? = null
    override fun initViewDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initClassViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initViews() {
        showFragment(SplashFragment.TAG, null, false)
        checkLoginStatus()
        adjustBottomBar()
        addContact()

    }

    private fun addContact() {
        binding.btnAddContact.setOnClickListener {
            contactDialog = ContactDialog(this, this)
            contactDialog?.show()

        }
    }

    private fun checkLoginStatus() {
        val authToken = CommonUtils.instance.getPref("authToken")
        android.os.Handler().postDelayed({
            if (authToken != null) {
                showFragment(HomeFragment.TAG, null, false)
            } else {
                showFragment(LoginFragment.TAG, null, false)
            }
        }, 3000)
    }

    private fun adjustBottomBar() {
        val homeMenuItem = binding.bottomNavigationBar.menu.findItem(R.id.home)
        homeMenuItem.isChecked = true
        binding.bottomNavigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    showFragment(HomeFragment.TAG, null, false)
                    binding.btnAddContact.visibility = View.GONE
                }
                R.id.contact -> showFragment(ContactFragment.TAG, null, false)
            }

            true
        }
    }

    override fun showBottomBar() {
        binding.bottomNavigationBar.visibility = View.VISIBLE
    }

    override fun hideBottomBar() {
        binding.bottomNavigationBar.visibility = View.GONE

    }

    override fun showAddContact() {
        binding.btnAddContact.visibility = View.VISIBLE
    }

    override fun hideAddContact() {
        binding.btnAddContact.visibility = View.GONE
    }

    override fun addContact(name: String, phone: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (name.isEmpty() || phone.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    val message = if (name.isEmpty()) "Name not null" else "Phone not null"
                    Toast.makeText(App.instance, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                val contact = Contact(name, phone)
                App.instance.getDb().contactDao().insertContact(contact)
                Log.i("Add", "ID: ${contact.id}, Name: ${contact.name}, Phone: ${contact.phoneNumber}")
            }
        }
    }
}