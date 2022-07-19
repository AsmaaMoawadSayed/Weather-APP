package com.asmaa.weather.domain

import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse

interface WeatherRepositoryContract{
    suspend fun getWeather(query:String): WeatherForecastResponse?
    suspend fun getCountries(query:String): MutableList<Country>?

}