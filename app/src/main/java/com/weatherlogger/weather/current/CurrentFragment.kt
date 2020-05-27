package com.weatherlogger.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.weatherlogger.R
import com.weatherlogger.data.network.ConnectivityInterceptorImpl
import com.weatherlogger.data.network.WeatherAPIService
import com.weatherlogger.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.current_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentFragment()
    }

    private lateinit var viewModel: CurrentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
        // TODO: Use the ViewModel
       // val apiService = WeatherAPIService()
        val apiService = WeatherAPIService(
            ConnectivityInterceptorImpl(this.requireContext()))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadMainWeather.observe(this, Observer {
            tv_current.text = it.main.toString()
        })
        GlobalScope.launch (Dispatchers.Main){
           weatherNetworkDataSource.getWeatherData("Vadodara")

        }
    }

}
