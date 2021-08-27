package com.example.w1_d3_networkandthreads


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val mHandler: Handler = object :
        Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            if (inputMessage.what == 0) {
                myString.text = inputMessage.obj.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isNetworkAvailable()) {
            val myRunnable = Conn(
                mHandler
            )
            val myThread = Thread(myRunnable)
            myThread.start()
        }
    }

    private fun isNetworkAvailable(): Boolean =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).isDefaultNetworkActive

}