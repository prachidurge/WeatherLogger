package com.weatherlogger.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherlogger.R
import com.weatherlogger.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentViewModelFactory by instance()
    private lateinit var viewModel: CurrentViewModel
    private lateinit var adapter: MainWeatherAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val mainWeather = viewModel.weather.await()
        mainWeather.observe(this@CurrentFragment, Observer {
            if (it == null) {
                return@Observer
            } else {
                val linearLayoutManager = LinearLayoutManager(this@CurrentFragment.context)
                rv_weather_data!!.layoutManager = linearLayoutManager
                adapter = MainWeatherAdapter(it)
                rv_weather_data.adapter = adapter
            }

        })
    }

}
