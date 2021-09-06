package com.example.w3_d1_graph


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val series = LineGraphSeries(
            arrayOf<DataPoint>(
                DataPoint(5.0, 9.0),
                DataPoint(10.5, 5.2),
                DataPoint(12.3, 3.8),
                DataPoint(23.9, 2.0),
                DataPoint(54.9, 6.0)
            )
        )
        graph.addSeries(series)

        graph.viewport.isScalable = true
        graph.viewport.isScrollable = true

        graph.title = "Graph View"



    }
}