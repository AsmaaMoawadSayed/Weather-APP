package com.asmaa.weather.data


import com.asmaa.weather.data.remote.NetworkDataSource
import com.asmaa.weather.data.remote.NetworkDataSourceContract
import com.asmaa.weather.data.remote.RetrofitBuilder
import com.asmaa.weather.data.remote.RetrofitServiceConnection
import com.asmaa.weather.domain.WeatherRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class WeatherModule {


    @Singleton
    @Provides
    fun provideRepository(networkDataSource: NetworkDataSource):WeatherRepositoryContract{
        return WeatherRepository(networkDataSource)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(retrofitServiceConnection: RetrofitServiceConnection):NetworkDataSourceContract{
        return NetworkDataSource(retrofitServiceConnection)
    }


    @Singleton
    @Provides
    fun provideRetrofitServiceConnection():RetrofitServiceConnection{
        return RetrofitBuilder().build()
    }
}