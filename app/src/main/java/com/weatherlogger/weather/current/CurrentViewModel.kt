package com.weatherlogger.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherlogger.data.entity.Main
import com.weatherlogger.data.repository.WeatherRepository

class CurrentViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    suspend fun getWeatherData(cityName: String): LiveData<out Main> {
        return weatherRepository.getWeatherData(cityName)
    }
}
