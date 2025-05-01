package com.example.weather.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.R
import com.example.weather.viewModel.GeoViewModel
import com.example.weather.viewModel.WeatherViewModel



@Composable
fun Weather() {
    val weatherVM: WeatherViewModel = viewModel()
    val geoVM: GeoViewModel = viewModel()

    val weather = weatherVM.weatherData
    val location = geoVM.locationName


    val lat = 25.0110
    val lon = 121.4628

    LaunchedEffect(Unit) {
        weatherVM.fetchWeather(lat, lon)
        geoVM.fetchLocationName(lat, lon)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (weather != null) {
            Text(text = "${stringResource(id = R.string.location)}：$location", fontSize = 20.sp)
            Text(text = "溫度：${weather.main?.temp?: "無資料"}°C", fontSize = 20.sp)
            Text(text = "天氣：${weather.weather?.firstOrNull()?.description ?: "無資料"}", fontSize = 20.sp)
        }else {
            Text(text = "載入中...", fontSize = 20.sp)
        }
    }
}
@Preview(showBackground = true, name = "Weather Preview")
@Composable
fun WeatherPreview() {
    Weather()
}