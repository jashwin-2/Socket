package com.example.socket

import android.content.Context
import android.util.Log
import java.lang.NumberFormatException

object Socket {
    private val TAG = Socket::class.java.simpleName
    private const val DEFAULT_PORT = 8081
    private var clientServer: ClientServer? = null
    private var addressLog = "not available"
    fun initialize(context: Context?) {
        var portNumber: Int
        try {
            portNumber = DEFAULT_PORT
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "PORT_NUMBER should be integer", ex)
            portNumber = DEFAULT_PORT
            Log.i(TAG, "Using Default port : $DEFAULT_PORT")
        }
        clientServer = ClientServer(context, portNumber)
        clientServer!!.start()
        addressLog = NetworkUtils.getAddressLog(context!!, portNumber)
        Log.d(TAG, addressLog)
    }

    fun getAddressLog(): String {
        Log.d(TAG, addressLog)
        return addressLog
    }

    fun shutDown() {
        if (clientServer != null) {
            clientServer!!.stop()
            clientServer = null
        }
    }

    val isServerRunning: Boolean
        get() = clientServer != null && clientServer!!.isRunning
}