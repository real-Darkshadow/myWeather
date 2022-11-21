package com.example.myweather.neteork

import com.example.myweather.forecastmodel.nfor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface nfr {
    @GET("forecast")
    fun getforecast(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("appid") appid:String,
        @Query("units") units:String
    ):Call<nfor>
}