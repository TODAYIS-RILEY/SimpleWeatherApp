package com.example.weather.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.R
import com.example.weather.model.cityList
import com.example.weather.ui.theme.SunnyBlue
import com.example.weather.ui.theme.cloudYellow
import com.example.weather.ui.theme.lakeBlue
import com.example.weather.ui.theme.lightBlue
import com.example.weather.ui.theme.littleRainBlue
import com.example.weather.ui.theme.rainBlue
import com.example.weather.ui.theme.stormPurple
import com.example.weather.ui.theme.sunnyOrange
import com.example.weather.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Weather() {
    val weatherVM: WeatherViewModel = viewModel()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { cityList.size })

    val weatherData = weatherVM.weatherData

    val weatherMain = weatherData?.weather?.firstOrNull()?.main
    val textColor = getDescriptionColor(weatherMain)

    val isLoading = weatherVM.isLoading

    LaunchedEffect(pagerState.currentPage) {
        val city = cityList[pagerState.currentPage]
        weatherVM.fetchWeather(city.lat, city.lon)
    }

    //Background Main entrance
    Column(  modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    SunnyBlue,
                    lightBlue,
                    lakeBlue
                ),
                start = Offset(0f, 0f),
                end = Offset.Infinite
            )
        )
        ) {

        // HorizontalPager：顯示當前選擇的城市天氣
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LocationDisplay(weatherData?.sys?.sunset?.let { unixToTime(it) }, cityList[page].name)
                        WeatherIconDisplay(weatherData?.weather?.firstOrNull()?.main)
                        TemperatureDisplay(weatherData?.main?.temp)
                        DescriptionDisplay(weatherData?.weather?.firstOrNull()?.description, textColor)
                    }
                }
            }
        }

        // 分頁指示器（圓點）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(cityList.size) { index ->
                val color = if (pagerState.currentPage == index) Color.White else Color.Gray
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(4.dp)
                        .background(color, shape = CircleShape)
                )
            }
        }
    }
}




//日期 地點
@Composable
fun LocationDisplay(day: String?, location: String) {
    Column(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp)

    ) {
        Text(
            text = day.toString(),
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = location,
            fontSize = 55.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.White,
                    offset = Offset(0f, 0f),
                    blurRadius = 6f
                )
            )
        )
    }
}

//大圖示
@Composable
fun WeatherIconDisplay(weatherMain: String?) {
    val iconRes = when (weatherMain) {
        "Clear" -> R.drawable.sun
        "Clouds" -> R.drawable.clouds
        "Rain" -> R.drawable.rain
        "Drizzle" -> R.drawable.rain
        "Snow" -> R.drawable.snow
        "Thunderstorm" -> R.drawable.storm
        else -> R.drawable.wind
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)
        )
        Text(weatherMain.toString())
    }
}

//溫度
@Composable
fun TemperatureDisplay(temperature: Double?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = "${temperature?.toInt()}°",
            fontSize = 130.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

//天氣描述
@Composable
fun DescriptionDisplay(description: String?, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = description.toString(),
            fontSize = 30.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )

    }
}

fun getDescriptionColor(weatherMain: String?): Color {
    return when (weatherMain) {
        "Clear" -> sunnyOrange
        "Clouds" -> cloudYellow
        "Rain" -> rainBlue
        "Snow" -> Color.White
        "Drizzle" -> littleRainBlue
        "Thunderstorm" -> stormPurple
        else -> cloudYellow
    }
}

fun unixToTime(unixTimestamp: Int): String {
    val date = Date(unixTimestamp * 1000L)
    val sdf = SimpleDateFormat("MMMM  dd, yyyy", Locale.getDefault())
    return sdf.format(date)
}

@Preview(showBackground = true, name = "Weather Preview")
@Composable
fun WeatherPreview() {
    Weather()
}