package com.weather.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weather.R

class WeatherFragment : Fragment() {
    companion object{
        val EXTRA_CITY_NAME = "com.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        return view
    }
}