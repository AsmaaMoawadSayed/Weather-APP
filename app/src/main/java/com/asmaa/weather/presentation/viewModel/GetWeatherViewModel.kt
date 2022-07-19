package com.asmaa.weather.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse
import com.asmaa.weather.domain.use_cases.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherViewModel @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase):ViewModel() {


      suspend fun  getWeather(query:String):WeatherForecastResponse?{

       var response:WeatherForecastResponse? = null

       val job= viewModelScope.launch {
           withContext(Dispatchers.IO){
               getWeatherUseCase.getWeather(query)?.let{
                   response=it
               }
           }
        }
         job.join()
          return response
    }


    suspend fun  getCountries(query:String):MutableList<Country>?{

        var response:MutableList<Country>? = null

        val job= viewModelScope.launch {
            withContext(Dispatchers.IO){
                getWeatherUseCase.getCountries(query)?.let{
                    response=it
                }
            }
        }
        job.join()
        return response
    }



}