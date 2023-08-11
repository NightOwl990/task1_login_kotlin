package com.example.task1_login_kotlin.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.namespace.databinding.FragmentHomeBinding
import com.example.task1_login_kotlin.view.interfaces.OnBottomCallBack
import com.example.task1_login_kotlin.viewmodel.CommonViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, CommonViewModel>() {
    companion object {
        val TAG: String = HomeFragment::class.java.name
    }

    override fun initViewModel(): Class<CommonViewModel> {
        return CommonViewModel::class.java
    }

    override fun initViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun initViews() {
        bottomCallBack.showBottomBar()
    }
}