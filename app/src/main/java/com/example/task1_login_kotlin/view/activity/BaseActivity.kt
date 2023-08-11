package com.example.task1_login_kotlin.view.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.namespace.R
import com.example.task1_login_kotlin.utils.CheckNetwork
import com.example.task1_login_kotlin.view.dialog.NetworkDialog
import com.example.task1_login_kotlin.view.fragment.BaseFragment
import com.example.task1_login_kotlin.view.interfaces.OnBottomCallBack
import com.example.task1_login_kotlin.view.interfaces.OnDialogCallBack
import com.example.task1_login_kotlin.view.interfaces.OnMainCallBack
import com.example.task1_login_kotlin.viewmodel.BaseViewModel

abstract class BaseActivity<T : ViewDataBinding, M : BaseViewModel> : AppCompatActivity(), OnMainCallBack, OnDialogCallBack, OnBottomCallBack{
    lateinit var binding: T
    lateinit var viewModel: M
    private var checkNetwork: CheckNetwork? = null
    private var dialog: NetworkDialog? = null
    companion object{
        const val TAG = "BaseActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewDataBinding()
        viewModel = ViewModelProvider(this)[initClassViewModel()]
        setContentView(binding.root)
        dialog = NetworkDialog(this)
        checkNetwork = CheckNetwork()
        checkNetwork!!.setCallBack(this)
        initViews()
    }

    abstract fun initViews()

    abstract fun initClassViewModel(): Class<M>

    abstract fun initViewDataBinding(): T

    override fun showFragment(tag: String, data: Any?, isBack: Boolean) {
        try {
            val clazz = Class.forName(tag)
            val cons = clazz.getConstructor()
            val fragment: BaseFragment<*, *> = cons.newInstance() as BaseFragment<*, *>
            fragment.setData(data)
            fragment.setCallBack(this)
            fragment.setOnBottomCallBack(this)
            val trans = supportFragmentManager.beginTransaction()
            if (isBack) {
                trans.addToBackStack(null)
            }
            trans.setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out
            )
            trans.replace(R.id.ln_main, fragment, tag).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showDialog() {
        dialog!!.show()
    }

    override fun cancelDialog() {
        dialog!!.dismiss()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "--------- onResume")
        registerReceiver(checkNetwork, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "--------- onPause")
        unregisterReceiver(checkNetwork)
    }
}