package com.example.weather.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.R
import com.example.weather.model.taiwanCities
import com.example.weather.viewModel.GeoViewModel
import com.example.weather.viewModel.WeatherViewModel
import kotlin.text.startsWith


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Weather() {
    val weatherVM: WeatherViewModel = viewModel()

    var selectedCity by remember { mutableStateOf(taiwanCities[0]) }
    var inputText by remember { mutableStateOf("") }
    val filteredOptions = taiwanCities.filter { it.name.startsWith(inputText, true) }
    var expanded by remember { mutableStateOf(false) }
    expanded = expanded && filteredOptions.isNotEmpty()

    LaunchedEffect(selectedCity) {
        weatherVM.fetchWeather(
            lat = selectedCity.lat,
            lon = selectedCity.lon,
        )
        inputText=""
    }

    val weather = weatherVM.weatherData

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                readOnly = false,
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, true),
                value = inputText,
                onValueChange = {
                    inputText = it
                    expanded = true
                },
                singleLine = true,
                label = { Text("輸入台灣縣市") },
                trailingIcon = { TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteredOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            selectedCity = option
                            inputText = option.name
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (weather != null) {
            Text(text = "位置：${selectedCity.name}", fontSize = 20.sp)
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