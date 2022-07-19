package com.asmaa.weather.data

import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse
import com.asmaa.weather.data.remote.NetworkDataSourceContract
import com.asmaa.weather.domain.WeatherRepositoryContract
import javax.inject.Inject


class WeatherRepository @Inject constructor(private val networkDataSourceContract: NetworkDataSourceContract):WeatherRepositoryContract {
    override suspend fun getWeather(query: String): WeatherForecastResponse? {
        return  networkDataSourceContract.getCurrentWeather(query)
  }

    override suspend fun getCountries(query: String): MutableList<Country>? {
        return  networkDataSourceContract.getCountries(query)
    }
}