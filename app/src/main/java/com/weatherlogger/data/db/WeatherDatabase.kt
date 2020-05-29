package com.weatherlogger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weatherlogger.data.entity.Main

@Database(
    entities = [Main::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun mainWeatherDao(): MainWeatherDao

    //making Database singleton
    companion object {

        @Volatile
        private var instance: WeatherDatabase? = null  // volatile
        private val LOCK = Any() //dummy object to make sure no 2 threads are doing the same task

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDB(context).also { instance = it }
        }

        private fun buildDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "weather.db"
            ).build()


    }
}



