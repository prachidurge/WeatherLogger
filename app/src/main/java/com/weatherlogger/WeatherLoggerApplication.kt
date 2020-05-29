package com.weatherlogger

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.weatherlogger.data.db.MainWeatherDao
import com.weatherlogger.data.db.WeatherDatabase
import com.weatherlogger.data.network.*
import com.weatherlogger.data.repository.WeatherRepository
import com.weatherlogger.data.repository.WeatherRepositoryImpl
import com.weatherlogger.weather.current.CurrentViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherLoggerApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherLoggerApplication))
        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().mainWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherAPIService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance()) }
        bind() from provider {CurrentViewModelFactory(instance())}


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}