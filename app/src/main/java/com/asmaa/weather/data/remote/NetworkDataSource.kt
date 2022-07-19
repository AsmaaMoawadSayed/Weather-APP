package com.asmaa.weather.data.remote

import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse
import javax.inject.Inject

class NetworkDataSource @Inject constructor( private val retrofitServiceConnection :RetrofitServiceConnection):NetworkDataSourceContract {

    override suspend fun getCurrentWeather(query:String): WeatherForecastResponse? {
          var response: WeatherForecastResponse?
        try {
            response =retrofitServiceConnection.getCurrentWeather(ConstantsNetwork.publicAPIKey,query,ConstantsNetwork.days,ConstantsNetwork.aqi,ConstantsNetwork.alerts)
        }
        catch (e:Exception){
            response = null
        }
       return response
    }


    override suspend fun getCountries(query:String): MutableList<Country>? {
        var response: MutableList<Country>?
        try {
            response =retrofitServiceConnection.getCountries(ConstantsNetwork.publicAPIKey,query)
        }
        catch (e:Exception){
            response = null
        }
        return response
    }

}