package com.asmaa.weather.data.models

data class WeatherForecastResponse(
    val current: Current,
    val location: Location,
    val forecast:Forecast
)