package com.example.socket

import android.content.Context
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.ServerSocket
import java.net.SocketException


class ClientServer(context: Context?, port: Int) : Runnable {
    private val mPort: Int = port
    private val mRequestHandler: RequestHandler = RequestHandler(context!!)
    var isRunning = false
        private set
    private var mServerSocket: ServerSocket? = null
    fun start() {
        isRunning = true
        Thread(this).start()
    }

    fun stop() {
        try {
            isRunning = false
            if (null != mServerSocket) {
                mServerSocket!!.close()
                mServerSocket = null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error closing the server socket.", e)
        }
    }

    override fun run() {
        try {
            mServerSocket = ServerSocket(mPort)
            while (isRunning) {
                Log.d(TAG, "runing")
                val socket = mServerSocket!!.accept()
                mRequestHandler.handle(socket)
                socket.close()
            }
        } catch (e: SocketException) {
            // The server was stopped; ignore.
        } catch (e: IOException) {
            Log.e(TAG, "Web server error.", e)
        } catch (ignore: Exception) {
            Log.e(TAG, "Exception.", ignore)
        }
    }

    companion object {
        private const val TAG = "ClientServer"
    }

}