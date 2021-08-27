package com.example.w1_d1_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentOne.FragmentOneListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentOne>(R.id.fragmentContainerView)
            }
        }
    }

    override fun onButtonClick(position: Int) {
        Log.d("USR", "MainActivity received $position")

        val bundle = bundleOf("pos" to position)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FragmentTwo>(R.id.fragmentContainerView, args = bundle)
            addToBackStack(null)


        }

        }
    }




