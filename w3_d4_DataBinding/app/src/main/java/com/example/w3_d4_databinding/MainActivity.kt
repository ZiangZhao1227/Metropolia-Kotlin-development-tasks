package com.example.w3_d4_databinding


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w3_d4_databinding.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import java.lang.NumberFormatException


class MyViewModel : ViewModel() {
    val Counter: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            it.value = initValue()
        }
    }
    var increment: Int? = null

    fun incCounter() {
        Counter.postValue(increment?.let { Counter.value?.plus(it) })
    }

    private fun initValue(): Int {
        return 0
    }
}

class MainActivity : AppCompatActivity() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(
        Dispatchers.Default +
                job
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userModel: MyViewModel by viewModels() // Obtain the ViewModel component.
        // Inflate view and obtain an instance of the binding class.
        val binding: ActivityMainBinding? =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding?.lifecycleOwner = this // in order to get updates to the layout
        binding?.viewmodel = userModel // Assign the component to a property in the binding class.

        val sharedPreference = getSharedPreferences("PREF", Context.MODE_PRIVATE)
        userModel.Counter.value =  sharedPreference.getInt("value",0)

        coroutineScope.launch {
            while (true) {
                delay(1000)
                userModel.incCounter()

                val editor = sharedPreference.edit()
                userModel.Counter.value?.let { editor.putInt("value", it) }
                editor.apply()
            }
        }

        et_editText.doAfterTextChanged {
                try {
                    val number: Int = et_editText.text.toString().toInt()
                    userModel.increment = number
                    userModel.incCounter()
                }catch (e:NumberFormatException){
                    Toast.makeText(this,"Please put an integer here ty",Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}