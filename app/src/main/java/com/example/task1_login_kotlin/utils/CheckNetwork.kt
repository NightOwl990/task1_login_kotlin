package com.example.task1_login_kotlin.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.example.task1_login_kotlin.view.interfaces.OnDialogCallBack

class CheckNetwork: BroadcastReceiver(){
    private lateinit var callBack: OnDialogCallBack

    fun setCallBack(callBack: OnDialogCallBack) {
        this.callBack = callBack
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val manager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            val network: Network? = manager.activeNetwork
            val capabilities: NetworkCapabilities? = manager.getNetworkCapabilities(network)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val info = manager.activeNetworkInfo
            info != null && info.isConnected
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (isNetworkAvailable(context)) {
            callBack.cancelDialog()
        } else {
            callBack.showDialog()
        }
    }
}