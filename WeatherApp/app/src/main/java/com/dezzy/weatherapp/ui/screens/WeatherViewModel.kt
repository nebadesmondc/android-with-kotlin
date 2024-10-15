package com.dezzy.weatherapp.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezzy.weatherapp.Constant
import com.dezzy.weatherapp.network.NetworkResponse
import com.dezzy.weatherapp.network.RetrofitInstance
import com.dezzy.weatherapp.network.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val weatherApiService = RetrofitInstance.apiService
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getWeatherData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            val response = weatherApiService.getCurrentWeather(apiKey = Constant.apiKey, location = city)
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e : Exception) {
                _weatherResult.value = NetworkResponse.Error(e.message ?: "Failed to load data")
            }
        }
    }
}