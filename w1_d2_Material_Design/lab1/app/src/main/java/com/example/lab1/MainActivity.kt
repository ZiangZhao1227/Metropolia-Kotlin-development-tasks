package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val username: String? = content_et.text.toString()

            hellotv.text = if (content_et.text.isEmpty()) {

                when (hellotv.text) {
                    resources.getString(R.string.hello_world) -> resources.getString(R.string.goodbye_summer)
                    resources.getString(R.string.goodbye_summer) -> resources.getString(R.string.hello_world)
                    else -> resources.getString(R.string.hello_world)
                }

            } else {
                "${resources.getString(R.string.hello)} $username "

            }
            content_et.text.clear()


        }

        floating_action_button.setOnClickListener {
            Toast.makeText(this, "FAB button clicked", Toast.LENGTH_SHORT).show()
        }
        floating_action_button.show()
    }

}