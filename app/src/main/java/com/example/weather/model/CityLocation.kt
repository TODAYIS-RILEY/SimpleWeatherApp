package com.example.weather.model

data class CityLocation(val name: String, val lat: Double, val lon: Double)

val taiwanCities = listOf(
    CityLocation("台北市", 25.0330, 121.5654),
    CityLocation("新北市", 25.0169, 121.4628),
    CityLocation("基隆市", 25.1309, 121.7390),
    CityLocation("桃園市", 24.9937, 121.3000),
    CityLocation("新竹市", 24.8039, 120.9647),
    CityLocation("新竹縣", 24.8385, 121.0031),
    CityLocation("苗栗縣", 24.5602, 120.8214),
    CityLocation("台中市", 24.1477, 120.6736),
    CityLocation("彰化縣", 24.0685, 120.5575),
    CityLocation("南投縣", 23.8388, 120.9876),
    CityLocation("雲林縣", 23.7092, 120.4313),
    CityLocation("嘉義市", 23.4801, 120.4491),
    CityLocation("嘉義縣", 23.4519, 120.2550),
    CityLocation("台南市", 22.9999, 120.2270),
    CityLocation("高雄市", 22.6273, 120.3014),
    CityLocation("屏東縣", 22.5510, 120.5487),
    CityLocation("宜蘭縣", 24.7021, 121.7378),
    CityLocation("花蓮縣", 23.9872, 121.6015),
    CityLocation("台東縣", 22.7566, 121.1444),
    CityLocation("澎湖縣", 23.5711, 119.5797),
    CityLocation("金門縣", 24.4367, 118.3186),
    CityLocation("連江縣", 26.1608, 119.9517)
)
