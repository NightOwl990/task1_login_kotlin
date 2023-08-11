package com.example.task1_login_kotlin.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.namespace.R
import com.example.namespace.databinding.FragmentLoginBinding
import com.example.task1_login_kotlin.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    companion object {
        val TAG: String = LoginFragment::class.java.name
        const val SAVE_USERNAME = "SAVE_USERNAME"
        const val SAVE_PASS = "SAVE_PASS"
        const val SAVE_CHECKED = "SAVE_CHECKED"
    }

    override fun initViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun initViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?,
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initViews() {
        viewModel.setDialogCallBack(this)

//        viewModel.username.value = CommonUtils.instance.getPref(SAVE_USERNAME)
//        viewModel.password.value = CommonUtils.instance.getPref(SAVE_PASS)
//        viewModel.isChecked.value = (CommonUtils.instance.getPref(SAVE_CHECKED)).toBoolean()

        CoroutineScope(Dispatchers.Main).launch {
            listenerResult()
        }

        binding.btnLogin.setOnClickListener { v ->
            v.startAnimation(AnimationUtils.loadAnimation(context,
                androidx.appcompat.R.anim.abc_fade_in))
            val username = binding.edtUsername.editText?.text.toString()
            val password = binding.edtPass.editText?.text.toString()
            viewModel.login(username, password)
        }
    }

    private fun listenerResult() {
        viewModel.res.observe(this) { check ->
            check?.let { resCheck ->
                if (resCheck == "Login successful"){
                    showToast(R.color.colorGreen, R.drawable.ic_check, resCheck)
                    mCallBack.showFragment(HomeFragment.TAG, null, false)
                } else {
                    showToast(R.color.colorRed, R.drawable.ic_notify, resCheck)
                }
            }
        }
    }
}