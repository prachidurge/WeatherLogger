package com.weatherlogger.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weatherlogger.data.WeatherResponse
import com.weatherlogger.utilities.NoConnectivityException
import java.lang.Exception

class WeatherNetworkDataSourceImpl(private val weatherAPIService:WeatherAPIService) : WeatherNetworkDataSource {
    private val _downloadMainWeather = MutableLiveData<WeatherResponse>()
    override val downloadMainWeather: LiveData<WeatherResponse>
        get() = _downloadMainWeather


    override suspend fun getWeatherData(name: String) {
        try {
            val  fetchedData = weatherAPIService.getWeatherData(name).await()
            _downloadMainWeather.postValue(fetchedData)
        }catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}