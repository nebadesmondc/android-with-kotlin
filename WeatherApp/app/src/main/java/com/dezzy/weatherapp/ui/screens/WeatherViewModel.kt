package com.dezzy.weatherapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel: ViewModel() {
    fun getWeatherData(city: String) {
        Log.i("WeatherViewModel", "Getting weather data for $city")
    }
}