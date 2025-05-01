package com.example.weather.server

import com.example.weather.model.GeocodingResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApiService {
        @GET("reverse")
        suspend fun reverseGeocoding(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") apiKey: String,
            @Query("lang") lang: String = "zh"
        ): Response<List<GeocodingResponseItem>>
}