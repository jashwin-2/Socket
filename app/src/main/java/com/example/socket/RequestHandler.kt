package com.example.socket

import android.content.Context
import android.content.res.AssetManager
import android.text.TextUtils
import android.util.Log
import com.example.socket.Utils.detectMimeType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*
import java.lang.Exception
import java.net.Socket

class RequestHandler(private val mContext: Context) {
    private val mAssets: AssetManager = mContext.resources.assets

    @Throws(IOException::class)
    fun handle(socket: Socket) {
        var reader: BufferedReader? = null
        var output: PrintStream? = null
        try {
            var route: String? = null

            // Read HTTP headers and parse out the route.
            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            var line: String
            while (!TextUtils.isEmpty(reader.readLine().also { line = it })) {
                if (line.startsWith("GET /")) {
                    val start = line.indexOf('/') + 1
                    val end = line.indexOf(' ', start)
                    route = line.substring(start, end)
                    break
                }
            }

            // Output stream that we send the response to
            output = PrintStream(socket.getOutputStream())
            if (route == null || route.isEmpty()) {
                route = "index.html"
                Log.d("Called", "handle:2323 ")
            }
            val bytes: ByteArray
            if (route.startsWith("audio")) {
                Log.d("Called", "handle: ")
                bytes = Utils.loadContent("audio_stats.json", mAssets)!!

            } else if (route.startsWith("video")) {
                bytes = Utils.loadContent("video_stats.json", mAssets)!!
            } else {
                bytes = Utils.loadContent(route, mAssets)!!
            }

            output.println("HTTP/1.0 200 OK")
            output.println("Content-Type: " + detectMimeType(route))
            output.println("Content-Length: " + bytes.size)
            output.println()
            output.write(bytes)
            output.flush()
        } finally {
            try {
                output?.close()
                reader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}