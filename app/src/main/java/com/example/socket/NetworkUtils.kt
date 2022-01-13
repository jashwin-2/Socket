package com.example.socket

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager


object NetworkUtils {
    fun getAddressLog(context: Context, port: Int): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress = wifiManager.connectionInfo.ipAddress
        @SuppressLint("DefaultLocale") val formattedIpAddress = String.format(
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
        return "Open \nhttp://$formattedIpAddress:$port\nin your browser"
    }
}