package com.example.task1_login_kotlin.view.activity

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.namespace.R
import com.example.namespace.databinding.ActivityMainBinding
import com.example.task1_login_kotlin.utils.CommonUtils
import com.example.task1_login_kotlin.view.dialog.ContactDialog
import com.example.task1_login_kotlin.view.fragment.ContactFragment
import com.example.task1_login_kotlin.view.fragment.HomeFragment
import com.example.task1_login_kotlin.view.fragment.LoginFragment
import com.example.task1_login_kotlin.view.fragment.SplashFragment
import com.example.task1_login_kotlin.view.interfaces.OnBottomCallBack
import com.example.task1_login_kotlin.viewmodel.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, CommonViewModel>(), OnBottomCallBack {
    private var contactDialog: ContactDialog? = null
    override fun initViewDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initClassViewModel(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }

    override fun initViews() {
        contactDialog = ContactDialog(this){insertContactIntoDB()}
        showFragment(SplashFragment.TAG, null, false)
        checkLoginStatus()
        adjustBottomBar()
        addContact()
    }

    private fun addContact() {
        val addContactLiveData = MutableLiveData<Pair<String, String>>()
        binding.btnAddContact.setOnClickListener {
            contactDialog?.show()
        }
    }

    private fun insertContactIntoDB() {
        contactDialog?.addContactIntoRoomDB()
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
//        binding.bottomNavigationBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_top_alpha))
    }

    override fun hideBottomBar() {
//        binding.bottomNavigationBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_down_alpha))
        binding.bottomNavigationBar.visibility = View.GONE

    }

    override fun showAddContact() {
        binding.btnAddContact.visibility = View.VISIBLE
    }

    override fun hideAddContact() {
        binding.btnAddContact.visibility = View.GONE
    }
}