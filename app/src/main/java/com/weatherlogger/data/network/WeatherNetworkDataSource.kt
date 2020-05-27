package com.weatherlogger.data.network

import androidx.lifecycle.LiveData
import com.weatherlogger.data.WeatherResponse

interface WeatherNetworkDataSource {
    val downloadMainWeather: LiveData<WeatherResponse>
    suspend fun getWeatherData(name:String)
}