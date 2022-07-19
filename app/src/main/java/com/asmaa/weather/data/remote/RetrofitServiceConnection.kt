package com.asmaa.weather.data.remote

import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceConnection {

    @GET("forecast.json?")
    suspend fun getCurrentWeather(@Query("key")key:String,
                                  @Query ("q")q:String,
                                  @Query ("days")days:String,
                                  @Query("aqi")aqi:String,
                                  @Query("alerts") alerts:String
    ): WeatherForecastResponse


    @GET("search.json?")
    suspend fun getCountries(@Query("key")key:String,
                                  @Query ("q")q:String,

    ): MutableList<Country>

}