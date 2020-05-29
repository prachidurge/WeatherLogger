package com.weatherlogger.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weatherlogger.data.repository.WeatherRepository


class CurrentViewModelFactory(private val repository: WeatherRepository):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentViewModel(repository) as T
    }
}