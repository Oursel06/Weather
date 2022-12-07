package com.weather.openweathermap

import com.weather.weather.Weather

fun mapOpenWeatherDataToWeather(weatherWrapper: WeatherWrapper) : Weather {
    val weatherFirst = weatherWrapper.weather.first()
    return Weather(description = weatherFirst.description,
    temperature = weatherWrapper.main.temperature,
    humidity = weatherWrapper.main.humidity,
    pressure = weatherWrapper.main.pressure,
    iconUrl = weatherFirst.icon
    )
}