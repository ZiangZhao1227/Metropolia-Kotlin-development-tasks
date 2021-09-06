package com.example.w2_d2internalsensor


import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var circle: TextView
    private var brightness: Sensor? = null
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var magnetic: Sensor? = null
    private lateinit var pb: CircularProgressBar
    var sidesNum = 0.0
    var upDownNum = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Keeps phone in light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        circle = tv_circle
        pb = circularProgressBar



        setUpAccelertometerSensor()
        setUpLightSensor()
        setUpGyroscopeSensor()
        setUpMagneticSensor()
    }

    private fun setUpAccelertometerSensor() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Specify the sensor you want to listen to
        if (accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
            )

        }

    }

    private fun setUpGyroscopeSensor() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscope != null) {
            sensorManager.registerListener(
                this,
                gyroscope,
                SensorManager.SENSOR_DELAY_NORMAL,
            )

        }

    }

    private fun setUpMagneticSensor() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (gyroscope != null) {
            sensorManager.registerListener(
                this,
                magnetic,
                SensorManager.SENSOR_DELAY_NORMAL,
            )

        }

    }

    private fun setUpLightSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (brightness != null) {
            sensorManager.registerListener(
                this,
                brightness,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }

    }


    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
     /*       Log.d(
                "Main",
                "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} "
            )*/

            // Sides = Tilting phone left(10) and right(-10)
            val sides = event.values[0]
            sidesNum = sides.toDouble()
            // Up/Down = Tilting phone up(10), flat (0), upside-down(-10)
            val upDown = event.values[1]
            upDownNum = upDown.toDouble()

            circle.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            // Changes the colour of the circle if it's completely flat
            if (upDown.toInt() == 0 && sides.toInt() == 0) {
                circle.setBackgroundResource(R.drawable.correct)
            } else {
                circle.setBackgroundResource(R.drawable.circle)
            }

        }

        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = event.values[0]
   /*         Log.d(
                "light",
                "onSensorChanged: sides ${light1} } "
            )*/

            circle.text = " up/down: ${upDownNum.toInt()}\n" +
                    "left/right: ${sidesNum.toInt()}\n " +
                    "Sensor: $light1\n${brightness(light1)}"
            pb.setProgressWithAnimation(light1)
        }

        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
         /*   Log.d(
                "gyro",
                "onSensorChanged: sides x ${event.values[0]} y ${event.values[1]} z ${event.values[2]} "
            )*/
            GxValue.text = "xValue: "+ event.values[0].toString()
            GyValue.text = "yValue: "+ event.values[1].toString()
            GzValue.text = "zValue: "+ event.values[2].toString()
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
   /*         Log.d(
                "gyro",
                "onSensorChanged: sides x ${event.values[0]} y ${event.values[1]} z ${event.values[2]} "
            )*/
            MxValue.text = "xValue: "+ event.values[0].toString()
            MyValue.text = "yValue: "+ event.values[1].toString()
            MzValue.text = "zValue: "+ event.values[2].toString()
        }


    }

    private fun brightness(brightness: Float): String {

        return when (brightness.toInt()) {
            0 -> "Totally black"
            in 1..50 -> "Dark"
            in 51..5000 -> "Normal"
            in 5001..22000 -> "Incredibly bright"
            else -> "This light will blind you"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_FASTEST)
    }


    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}

