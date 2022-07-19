package com.asmaa.weather.domain.use_cases

import com.asmaa.weather.domain.WeatherRepositoryContract
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCharactersUseCaseTest {


    @Test
    fun getCharachters() = runBlocking {

        val fakeResult: List<Result?> = listOf()


        val fakeRepository: WeatherRepositoryContract = object : WeatherRepositoryContract {
            override suspend fun getCharachters(limit: Int, offset: Int): List<Result?>{
                return fakeResult
            }

        }

       val getCharachter =  GetWeatherUseCase(fakeRepository)
       val result = getCharachter.getCharachters(10,1)
       assert(result==fakeResult)

    }

}