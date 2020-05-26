package com.weatherlogger.data


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
@Entity(tableName = "tbl_weather")
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)