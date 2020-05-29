package com.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.weatherlogger.data.WeatherResponse
import com.weatherlogger.data.db.MainWeatherDao
import com.weatherlogger.data.entity.Main
import com.weatherlogger.data.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class WeatherRepositoryImpl(
    private val weatherDao: MainWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : WeatherRepository {
    init {
        weatherNetworkDataSource.downloadMainWeather.observeForever { newMainWeather ->
            saveMainWeather(newMainWeather)

        }
    }

    override suspend fun getWeatherData(cityName:String): LiveData<out Main> {
        return withContext(Dispatchers.IO) {
            initWeatherData(cityName)
            return@withContext weatherDao.getMainWeatherData()
        }
    }

    // for persistence
    private fun saveMainWeather(newMainWeatherResponse: WeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.insertOrUpdate(newMainWeatherResponse.main)
        }
    }

    private suspend fun initWeatherData(cityName:String) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchMainWeather(cityName)
        }
    }

    private suspend fun fetchMainWeather(cityName:String) {
        weatherNetworkDataSource.getWeatherData(cityName)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}