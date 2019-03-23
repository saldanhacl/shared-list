package br.com.go5.sharedlist.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Utils(private val context: Context) {

    fun isConnectedToInternet(): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        return info!=null && info.isConnected
    }
}