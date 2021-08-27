package com.example.w1_d3_networkandthreads

import android.util.Log
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class Conn(
    mHand: android.os.Handler,
) : Runnable {
    private val myHandler = mHand
    override fun run() {
        try {
            val myUrl = URL("https://users.metropolia.fi/~jarkkov/koe.txt")
            val myConn = myUrl.openConnection()
                    as HttpURLConnection
            val istream: InputStream =
                myConn.inputStream
            val allText = istream.bufferedReader().use {
                it.readText()
            }
            val result = StringBuilder()
            result.append(allText)
            val str = result.toString()

            val msg = myHandler.obtainMessage()
            msg.what = 0
            msg.obj = str
            myHandler.sendMessage(msg)
        } catch (e: Exception) {
            Log.e("err","$e something wrong here")
        }
    }
}