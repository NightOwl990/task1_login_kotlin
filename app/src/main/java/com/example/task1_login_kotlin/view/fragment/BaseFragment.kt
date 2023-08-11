package com.example.task1_login_kotlin.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.namespace.R
import com.example.task1_login_kotlin.view.dialog.ProgressBarDialog
import com.example.task1_login_kotlin.view.interfaces.OnBottomCallBack
import com.example.task1_login_kotlin.view.interfaces.OnDialogCallBack
import com.example.task1_login_kotlin.view.interfaces.OnMainCallBack
import com.example.task1_login_kotlin.viewmodel.BaseViewModel

abstract class BaseFragment<T : ViewDataBinding, M : BaseViewModel> : Fragment(), OnDialogCallBack {
    lateinit var mContext: Context
    lateinit var binding: T
    lateinit var viewModel: M
    var mData: Any? = null
    lateinit var mCallBack: OnMainCallBack
    lateinit var dialogCallBack: OnDialogCallBack
    lateinit var dialogProgress: ProgressBarDialog
    lateinit var bottomCallBack: OnBottomCallBack

    fun setOnBottomCallBack(callBack: OnBottomCallBack) {
        bottomCallBack = callBack
    }

    fun setCallBack(callBack: OnMainCallBack) {
        mCallBack = callBack
    }

    fun setData(data: Any?) {
        mData = data
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = initViewDataBinding(inflater, container)
        viewModel = ViewModelProvider(this)[initViewModel()]
        dialogProgress = ProgressBarDialog(mContext)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    abstract fun initViews()

    abstract fun initViewModel(): Class<M>

    abstract fun initViewDataBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected fun showToast(color: Int, img: Int, msg: Int) {
        showToast(color, img, getString(msg))
    }

    @SuppressLint("InflateParams")
    protected fun showToast(color: Int, img: Int, msg: String) {
        val toast = Toast(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.toast_login, null)
        val ivToast = view.findViewById<ImageView>(R.id.iv_toast)
        val tvToast = view.findViewById<TextView>(R.id.tv_toast)
        view.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, color))
        ivToast.setImageResource(img)
        tvToast.text = msg
        toast.view = view
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 100)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    override fun showDialog() {
        dialogProgress.show()
    }

    override fun cancelDialog() {
        dialogProgress.dismiss()
    }

}