package com.weatherlogger.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherlogger.data.entity.CURRENT_WEATHER_ID
import com.weatherlogger.data.entity.Main

@Dao
interface MainWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(mainEntry: Main)

    @Query("Select * from tbl_main_data where id=${CURRENT_WEATHER_ID}")
    fun getMainWeatherData():LiveData<Main>
}