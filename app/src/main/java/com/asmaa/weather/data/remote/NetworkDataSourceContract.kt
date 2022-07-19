package com.asmaa.weather.data.remote

import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse

interface NetworkDataSourceContract {
    suspend fun getCurrentWeather(query:String):WeatherForecastResponse?
    suspend fun getCountries(query:String): MutableList<Country>?
}