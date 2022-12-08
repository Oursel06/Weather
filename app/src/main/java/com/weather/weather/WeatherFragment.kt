package com.weather.weather

import android.app.VoiceInteractor.PickOptionRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import com.weather.App
import com.weather.R
import com.weather.openweathermap.WeatherWrapper
import com.weather.openweathermap.mapOpenWeatherDataToWeather
import kotlinx.android.synthetic.main.item_city.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {
    companion object{
        val EXTRA_CITY_NAME = "com.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var cityName: String
    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        refreshLayout = view.findViewById(R.id.swipe_refresh)
        cityName = WeatherFragment.EXTRA_CITY_NAME
        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)
        refreshLayout.setOnRefreshListener { refreshWeather() }
        return view
    }

    private fun refreshWeather() {
        this.cityName = requireActivity().intent.getStringExtra(EXTRA_CITY_NAME).toString()
        if (!refreshLayout.isRefreshing){
            refreshLayout.isRefreshing = true
        }
        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object: Callback<WeatherWrapper> {
            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>?
            ) {
                response?.body()?.let { val weather = mapOpenWeatherDataToWeather(it)
                    refreshLayout.isRefreshing = false
                    var wicon = Picasso.get()
                    if (weather.iconUrl == "01d"){
                        wicon.load(R.drawable.w01d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                    }
                    if (weather.iconUrl == "03d"){
                        wicon.load(R.drawable.w03d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                    }
                    if (weather.iconUrl == "04d"){
                        wicon.load(R.drawable.w04d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                    }
                    if (weather.iconUrl == "10d"){
                        wicon.load(R.drawable.w10d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                    }
                    if (weather.iconUrl == "13d"){
                        wicon.load(R.drawable.w13d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                    }
                    if (weather.iconUrl == "50d"){
                        wicon.load(R.drawable.w50d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                    }
                    city.text = "Ville : ${cityName}"
                    weatherDescription.text =  weather.description
                    temperature.text = weather.temperature.toString() + "°C"
                    humidity.text = weather.humidity.toString() + "%"
                    pressure.text = weather.pressure.toString() + " hPa"
                }
                Log.i("REPONSE", "Openweather : ${response?.body()}")
            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                refreshLayout.isRefreshing = false
                Toast.makeText(activity, "Aucune réponse", Toast.LENGTH_SHORT).show()
                Log.e("REPONSE", "ERREUR de récupération openweather")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME)){
            this.cityName = requireActivity().intent.getStringExtra(EXTRA_CITY_NAME).toString()
            if (!refreshLayout.isRefreshing){
                refreshLayout.isRefreshing = true
            }
            val call = App.weatherService.getWeather("$cityName,fr")
            call.enqueue(object: Callback<WeatherWrapper> {
                override fun onResponse(
                    call: Call<WeatherWrapper>,
                    response: Response<WeatherWrapper>?
                ) {
                    response?.body()?.let { val weather = mapOpenWeatherDataToWeather(it)
                        refreshLayout.isRefreshing = false
                        var wicon = Picasso.get()
                        if (weather.iconUrl == "01d"){
                            wicon.load(R.drawable.w01d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                        }
                        if (weather.iconUrl == "03d"){
                            wicon.load(R.drawable.w03d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                        }
                        if (weather.iconUrl == "04d"){
                            wicon.load(R.drawable.w04d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                        }
                        if (weather.iconUrl == "10d"){
                            wicon.load(R.drawable.w10d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                        }
                        if (weather.iconUrl == "13d"){
                            wicon.load(R.drawable.w13d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                        }
                        if (weather.iconUrl == "50d"){
                            wicon.load(R.drawable.w50d).placeholder(R.drawable.cloud_off).into(weatherIcon)
                        }
                        city.text = "Ville : ${cityName}"
                        weatherDescription.text =  weather.description
                        temperature.text = weather.temperature.toString() + "°C"
                        humidity.text = weather.humidity.toString() + "%"
                        pressure.text = weather.pressure.toString() + " hPa"
                    }
                    Log.i("REPONSE", "Openweather : ${response?.body()}")
                }

                override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    Toast.makeText(activity, "Aucune réponse", Toast.LENGTH_SHORT).show()
                    Log.e("REPONSE", "ERREUR de récupération openweather")
                }
            })
        }
    }
}