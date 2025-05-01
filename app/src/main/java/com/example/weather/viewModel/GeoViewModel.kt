package com.example.weather.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.server.GeoRetrofitInstance
import kotlinx.coroutines.launch

class GeoViewModel : ViewModel() {
    var locationName by mutableStateOf("未知")
        private set

    fun fetchLocationName(lat: Double, lon: Double) {
        viewModelScope.launch{
            try {
                val response = GeoRetrofitInstance.api.reverseGeocoding(
                    lat = lat,
                    lon = lon,
                    apiKey = "df5de48c0ec8bc1451712469556276e9"
                )
                if (response.isSuccessful) {
                    val geoResult = response.body()?.firstOrNull()
                    locationName = geoResult?.localNames?.zh.toString()
                } else {
                    Log.e("GeoVM", "查詢地點失敗：${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("GeoVM", "Exception: ${e.message}")
            }
        }
    }
}