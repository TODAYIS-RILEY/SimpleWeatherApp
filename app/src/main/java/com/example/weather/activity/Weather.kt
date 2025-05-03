package com.example.weather.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.R
import com.example.weather.viewModel.GeoViewModel
import com.example.weather.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Weather() {
    val weatherVM: WeatherViewModel = viewModel()
    val geoVM: GeoViewModel = viewModel()

    val weatherData = weatherVM.weatherData
    val location = geoVM.locationName


    val lat = 42.951384
    val lon = 89.189655

    LaunchedEffect(Unit) {
        weatherVM.fetchWeather(lat, lon)
        geoVM.fetchLocationName(lat, lon)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 顯示城市名稱
                Text(text = location, fontSize = 24.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                // 顯示氣象資料
                Text(
                    text = "天氣：${weatherData?.weather?.firstOrNull()?.description ?: "無資料"}",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "主要類型：${weatherData?.weather?.firstOrNull()?.main ?: "無資料"}",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 顯示溫度資料
                Text(
                    text = "溫度：${weatherData?.main?.temp ?: "無資料"}°C",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "體感溫度：${weatherData?.main?.feelsLike ?: "無資料"}°C",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "最高溫度：${weatherData?.main?.tempMax ?: "無資料"}°C",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "最低溫度：${weatherData?.main?.tempMin ?: "無資料"}°C",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 顯示濕度和氣壓
                Text(
                    text = "濕度：${weatherData?.main?.humidity ?: "無資料"}%",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "氣壓：${weatherData?.main?.pressure ?: "無資料"} hPa",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 顯示風速資料
                Text(
                    text = "風速：${weatherData?.wind?.speed ?: "無資料"} m/s",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "風向：${weatherData?.wind?.deg ?: "無資料"}°",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 顯示雲層資料
                Text(
                    text = "雲層覆蓋：${weatherData?.clouds?.all ?: "無資料"}%",
                    fontSize = 20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 顯示可見度資料
                Text(text = "可見度：${weatherData?.visibility?.let { it / 1000 } ?: "無資料"} 公里",
                    fontSize = 20.sp,
                    color = Color.White)

                Spacer(modifier = Modifier.height(8.dp))

                // 顯示日出和日落時間
                val sunriseTime = weatherData?.sys?.sunrise?.let { unixToTime(it) }
                val sunsetTime = weatherData?.sys?.sunset?.let { unixToTime(it) }

                Text(text = "日出時間：$sunriseTime", fontSize = 20.sp, color = Color.White)
                Text(text = "日落時間：$sunsetTime", fontSize = 20.sp, color = Color.White)
            }

    }
}
fun unixToTime(unixTime: Int): String {
    val date = Date(unixTime * 1000L)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}
@Preview(showBackground = true, name = "Weather Preview")
@Composable
fun WeatherPreview() {
    Weather()
}