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

    private val weatherCache = mutableMapOf<Pair<Double, Double>, ResponseApi>()
    var weatherData by mutableStateOf<ResponseApi?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun fetchWeather(lat: Double, lon: Double) {
        val key = lat to lon
        val cached = weatherCache[key]

        if (cached != null) {
            weatherData = cached
            isLoading = false
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.getCurrentWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = "df5de48c0ec8bc1451712469556276e9"
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        weatherData = it
                        weatherCache[key] = it // 加入快取
                    }
                } else {
                    Log.e("WeatherVM", "天氣查詢失敗：${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("WeatherVM", "Exception: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
