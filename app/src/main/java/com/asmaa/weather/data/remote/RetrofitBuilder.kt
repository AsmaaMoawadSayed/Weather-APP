package com.asmaa.weather.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    fun build(): RetrofitServiceConnection {
        val client= OkHttpClient().newBuilder().addInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY)).build()
        return Retrofit.Builder()
            .baseUrl(ConstantsNetwork.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RetrofitServiceConnection::class.java)


    }
}