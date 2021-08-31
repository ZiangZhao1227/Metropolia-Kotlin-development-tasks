package com.example.w2_d1_location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem


class MapActivity : AppCompatActivity() {
    private lateinit var marker: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )

        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )


        setContentView(R.layout.activity_map)

        map.setTileSource(TileSourceFactory.MAPNIK)


        map.setTileSource(TileSourceFactory.MAPNIK)

        map.setMultiTouchControls(true)

        map.controller.setZoom(13.0)



        marker = Marker(map)
        marker.icon =
            AppCompatResources.getDrawable(this, R.drawable.ic_baseline_person_pin_circle_24)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

        val latitudeNum = intent.getDoubleExtra("latitude", 0.0)
        val longitudeNum = intent.getDoubleExtra("longitude", 0.0)
        val altitudeNum = intent.getDoubleExtra("altitude", 0.0)
        val locationName = intent.getStringExtra("street")

        if (latitudeNum == 0.0 && longitudeNum == 0.0) {
            map.controller.setCenter(GeoPoint(60.17, 24.95))
            marker.position = GeoPoint(60.17, 24.95)
            marker.title = "Street name: Yliopistonkatu1, 00100 Helsinki" +
                    " Latitude: 60.17" + " Longitude: 24.95" + " Altitude: 35.70000"

        } else {
            map.controller.setCenter(GeoPoint(latitudeNum, longitudeNum))
            marker.position = GeoPoint(latitudeNum, longitudeNum)
            marker.title = "Street name: $locationName" +
                    " Latitude: $latitudeNum" + " Longitude: $longitudeNum" + " $altitudeNum: 35.70000"
        }

        marker.closeInfoWindow()
        map.overlays.add(marker)

        map.invalidate()


    }
}