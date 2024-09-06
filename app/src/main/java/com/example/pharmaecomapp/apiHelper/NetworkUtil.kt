package com.example.pharmaecomapp.apiHelper

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {
    public fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}