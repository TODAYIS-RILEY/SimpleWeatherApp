package com.example.weather.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val sharedOkHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

private val sharedRetrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .client(sharedOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object RetrofitInstance {
    val api: WeatherApiService by lazy {
        sharedRetrofit.create(WeatherApiService::class.java)
    }
    val Geoapi: GeoApiService by lazy {
        sharedRetrofit.create(GeoApiService::class.java)
    }
}
