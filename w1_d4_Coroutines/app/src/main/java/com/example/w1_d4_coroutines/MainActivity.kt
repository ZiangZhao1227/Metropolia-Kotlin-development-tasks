package com.example.w1_d4_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainActivity : AppCompatActivity() {
    class Account {
        private val mutex = Mutex()
        private var amount: Double = 0.0
        suspend fun deposit_coroutine(amount: Double) {
            mutex.withLock {
                val x = this.amount
                delay(1) // simulates processing time
                this.amount = x + amount
            }

        }

        fun saldo(): Double = amount
    }

    /* Approximate measurement of the given block's execution time */
    fun withTimeMeasurement(title: String, isActive: Boolean = true, code: () -> Unit) {
        if (!isActive) return
        val timeStart = System.currentTimeMillis()
        code()
        val timeEnd = System.currentTimeMillis()
        println("operation in '$title' took ${(timeEnd - timeStart)} ms")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tili2 = Account();
        withTimeMeasurement("Single coroutine deposit 1000 times") {
            runBlocking {
                launch {
                    for (i in 1..1000)
                        tili2.deposit_coroutine(0.0)
                }
            }
            findViewById<TextView>(R.id.myTV).text = "Saldo2: ${tili2.saldo()}"
        }
        withTimeMeasurement("Two coroutines together", isActive = true) {
            runBlocking {
                launch {
                    for (i in 1..1000) tili2.deposit_coroutine(1.0)
                }
                launch {
                    for (i in 1..1000) tili2.deposit_coroutine(1.0)
                }

            }
            findViewById<TextView>(R.id.myTV).text = "Saldo2: ${tili2.saldo()}"
        }
    }
}