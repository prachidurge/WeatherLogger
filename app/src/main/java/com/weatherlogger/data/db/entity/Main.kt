package com.weatherlogger.data.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0
@Entity(tableName = "tbl_main_data")
data class Main(
    @SerializedName("feels_like")
    @ColumnInfo(name = "feels_like")
    val feelsLike: Double,
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    @SerializedName("temp_max")
    @ColumnInfo(name =  "temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    @ColumnInfo(name= "temp_min")
    val tempMin: Double
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}