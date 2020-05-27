package com.weatherlogger.weather.current

import androidx.lifecycle.ViewModel
import com.weatherlogger.data.repository.WeatherRepository
import com.weatherlogger.utilities.lazyDeferred

class CurrentViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    val weather by lazyDeferred {
        weatherRepository.getWeatherData()
    }
}
