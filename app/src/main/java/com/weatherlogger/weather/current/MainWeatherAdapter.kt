package com.weatherlogger.weather.current

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherlogger.R
import com.weatherlogger.data.entity.Main

class MainWeatherAdapter(private val mainWeather: Main): RecyclerView.Adapter<MainWeatherAdapter.MainWeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainWeatherHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainWeatherHolder(inflater, parent)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: MainWeatherHolder, position: Int) {
        holder.bind(mainWeather)
    }


    class MainWeatherHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_weather_item, parent, false)) {
        private var mTemperaturText: TextView? = null
        private var mTimeText: TextView? = null


        init {
            mTemperaturText = itemView.findViewById(R.id.textView_temperature)
            mTimeText = itemView.findViewById(R.id.textView_date)
        }


        fun bind(mainWeather: Main) {
            mTemperaturText?.text = "${mainWeather.temp}"
        }

    }
}