package com.weather.weather

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cityName: String? = intent.getStringExtra(WeatherFragment.EXTRA_CITY_NAME)
        Log.i("TAG", "City => ${cityName}")
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, WeatherFragment.newInstance())
            .commit()
    }
}