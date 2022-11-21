package com.example.myweather.neteork

import com.example.myweather.models.opendata
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherservice {
    @GET("weather")
    fun getweather(@Query("lat") lat:String,
                   @Query("lon") lon:String,
                   @Query("appid") appid: String?,
                   @Query("units") units: String?

    ):Call<opendata>
}