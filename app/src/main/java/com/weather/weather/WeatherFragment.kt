package com.weather.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.weather.App
import com.weather.R
import com.weather.openweathermap.WeatherWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {
    companion object{
        val EXTRA_CITY_NAME = "com.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }
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
        cityName = ""
        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME)){
            this.cityName = cityName
//            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
            val call = App.weatherService.getWeather("$cityName,fr")
            call.enqueue(object: Callback<WeatherWrapper> {
                override fun onResponse(
                    call: Call<WeatherWrapper>,
                    response: Response<WeatherWrapper>?
                ) {
                    Toast.makeText(activity, "Réponse : ${response?.body()}", Toast.LENGTH_SHORT).show()
                    Log.i("REPONSE", "Openweather : ${response?.body()}")
                }

                override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                    Toast.makeText(activity, "Aucune réponse", Toast.LENGTH_SHORT).show()
                    Log.e("REPONSE", "ERREUR de récupération openweather")
                }
            })
        }
    }
}