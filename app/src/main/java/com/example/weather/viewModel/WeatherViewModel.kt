package com.example.weather.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.server.RetrofitInstance
import com.example.weather.model.ResponseApi
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {
    var weatherData by mutableStateOf<ResponseApi?>(null)
        private set

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = "df5de48c0ec8bc1451712469556276e9"
                )
                if (response.isSuccessful) {
                    weatherData = response.body()
                } else {
                    Log.e("WeatherVM", "天氣查詢失敗：${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("WeatherVM", "Exception: ${e.message}")
            }
        }
    }
}