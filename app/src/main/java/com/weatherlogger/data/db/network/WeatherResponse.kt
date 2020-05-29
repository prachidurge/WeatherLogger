package com.weatherlogger.data


import com.google.gson.annotations.SerializedName
import com.weatherlogger.data.entity.Main
import com.weatherlogger.data.entity.Sys

data class WeatherResponse(
    val base: String,
    //val clouds: Clouds,
    val cod: Int,
   // val coord: Coord,
    val dt: Int,
    val id: Int,
  //  @SerializedName("Main")
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int//,
    //val weather: List<Weather>,
   // val wind: Wind
)