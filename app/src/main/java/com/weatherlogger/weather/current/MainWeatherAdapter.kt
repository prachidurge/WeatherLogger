package com.weatherlogger.weather.current

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherlogger.R
import com.weatherlogger.data.entity.Main
import com.weatherlogger.utilities.custom.RecyclerViewClickListener
import org.threeten.bp.ZonedDateTime

class MainWeatherAdapter(
    private val mainWeather: Main,
    private val cityName: String,
    internal var recyclerViewClickListener: RecyclerViewClickListener?
): RecyclerView.Adapter<MainWeatherAdapter.MainWeatherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainWeatherHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainWeatherHolder(inflater, parent, recyclerViewClickListener)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: MainWeatherHolder, position: Int) {
        holder.bind(mainWeather, cityName)
    }


    class MainWeatherHolder(inflater: LayoutInflater, parent: ViewGroup, val listener: RecyclerViewClickListener?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_weather_item, parent, false)) {
        private var mTemperaturText: TextView? = null
        private var mTimeText: TextView? = null
        private var mCityText: TextView? = null


        init {
            mTemperaturText = itemView.findViewById(R.id.textView_temperature)
            mTimeText = itemView.findViewById(R.id.textView_date)
            mCityText = itemView.findViewById(R.id.textView_city_name)
            itemView.setOnClickListener {
                listener!!.onRowClicked(adapterPosition)
            }
        }


        fun bind(mainWeather: Main, cityName: String) {
            mTemperaturText?.text = "Temprature: ${mainWeather.temp}\u2103 C"
            mTimeText?.text = "Time: ${ZonedDateTime.now()}"
            mCityText?.text = "City: ${cityName}"

        }

    }
}