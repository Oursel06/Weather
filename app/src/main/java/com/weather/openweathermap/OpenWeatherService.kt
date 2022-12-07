package com.weather.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "159bc8edfd02cf9d326776ae9269e0c8"
interface OpenWeatherService {
    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(@Query("q") cityName : String,
                   @Query("appid") apiKey: String = API_KEY) : Call<WeatherWrapper>

}