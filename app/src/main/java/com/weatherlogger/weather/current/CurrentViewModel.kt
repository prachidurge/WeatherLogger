package com.weatherlogger.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherlogger.data.entity.Main
import com.weatherlogger.data.repository.WeatherRepository
import com.weatherlogger.utilities.lazyDeferred

class CurrentViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    suspend fun getWeatherData(cityName: String): LiveData<out Main> {
        return weatherRepository.getWeatherData(cityName)
    }
}
