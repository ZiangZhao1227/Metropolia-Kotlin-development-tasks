package com.example.w1_d4_coroutinesnet


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.IOException
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isNetworkAvailable()) {
            val imgUrl = URL("https://placedog.net/640/480?random")
            button.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    val myImg = async(Dispatchers.IO) { getImg(imgUrl) }
                    showRes(myImg.await())
                }
            }
        }
    }

    private suspend fun getImg(url: URL): Bitmap? {
        return try {
            val myConn = url.openConnection()
            val istream = myConn.getInputStream()
            BitmapFactory.decodeStream(istream)
        } catch (e: IOException) {
            null
        }
    }

    private fun showRes(serverImg: Bitmap?) {
        imageView.setImageBitmap(serverImg)
    }

    private fun isNetworkAvailable(): Boolean =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).isDefaultNetworkActive
}








