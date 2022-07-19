package com.asmaa.weather.domain.use_cases

import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse
import com.asmaa.weather.domain.WeatherRepositoryContract
import javax.inject.Inject


class GetWeatherUseCase @Inject constructor(private val weatherCurrentRepository: WeatherRepositoryContract) {

    suspend fun getWeather(query:String):WeatherForecastResponse? {
        return weatherCurrentRepository.getWeather(query)
    }

    suspend fun getCountries(query:String):MutableList<Country>? {
        return weatherCurrentRepository.getCountries(query)
    }

}