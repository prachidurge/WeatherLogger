package com.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.weatherlogger.data.entity.Main

interface WeatherRepository {
    suspend fun getWeatherData(): LiveData<out Main>
}