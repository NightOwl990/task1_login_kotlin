package com.example.task1_login_kotlin.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.namespace.databinding.FragmentSplashBinding
import com.example.task1_login_kotlin.viewmodel.CommonViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, CommonViewModel>() {
    companion object {
        val TAG: String = SplashFragment::class.java.name
    }

    override fun initViewModel(): Class<CommonViewModel> = CommonViewModel::class.java

    override fun initViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initViews() {
        binding.animationView.playAnimation()
        binding.animationView.setAnimation("Logo.json")

    }
}