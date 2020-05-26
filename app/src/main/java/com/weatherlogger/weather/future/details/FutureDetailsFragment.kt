package com.weatherlogger.weather.future.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.weatherlogger.R

class FutureDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = FutureDetailsFragment()
    }

    private lateinit var viewModel: FutureDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FutureDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
