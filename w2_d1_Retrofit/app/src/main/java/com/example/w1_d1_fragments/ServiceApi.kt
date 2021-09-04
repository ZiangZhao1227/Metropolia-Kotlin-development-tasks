package com.example.w1_d1_fragments

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ServiceApi {
    const val URL = "https://en.wikipedia.org/w/"

    object Model {
        data class Result(@SerializedName("query") val query: Query)
        data class Query(@SerializedName("searchinfo") val searchinfo: SearchInfo)
        data class SearchInfo(@SerializedName("totalhits") val totalhits: Int)

    }

    interface Service {
        @GET("api.php?action=query&format=json&list=search")
        suspend fun userName(@Query("srsearch") action: String): Model.Result
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(Service::class.java)!!
}
