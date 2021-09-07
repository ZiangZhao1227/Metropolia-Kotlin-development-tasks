package com.example.w2_d1_location


import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationListener
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import android.content.Intent
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity(), LocationListener {
    var latitudeNum = 0.00
    var longitudeNum = 0.00
    var altitudeNum = 0.00
    var locationName = "Yliopistonkatu1, 00100 Helsinki"
    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if ((Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }

        start.setOnClickListener {
            lm.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                this
            )
            lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                this
            )
            Toast.makeText(this,"Start tracking location",Toast.LENGTH_SHORT).show()


        }

        end.setOnClickListener {
            lm.removeUpdates(this)
            Toast.makeText(this,"Stop tracking location",Toast.LENGTH_SHORT).show()


        }

        showMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("latitude", latitudeNum);
            intent.putExtra("longitude", longitudeNum);
            intent.putExtra("altitude", altitudeNum);
            intent.putExtra("street", locationName);
            startActivity(intent)
        }


    }

    override fun onLocationChanged(p0: Location) {
        try {
            val d = Log.d(
                "GEOLOCATION",
                "new latitude: ${p0.latitude} and longitude: ${p0.longitude} speed ${p0.speed} provider ${p0.provider}"
            )
            latitudeNum = p0.latitude
            longitudeNum = p0.longitude
            altitudeNum = p0.altitude


            val Home = Location("myLocation")
            Home.latitude = 60.2031467
            Home.longitude = 24.655600

            val street = getAddress(p0.latitude, p0.longitude)
            locationName = street
            streetName.text = "current location: $street"

            val distance = Home.distanceTo(p0)
            Myhome.text = "Far from home: $distance meters"



        } catch (e: IOException) {
            streetName.text = "check your internet connection to show street name"
        }

    }


}





