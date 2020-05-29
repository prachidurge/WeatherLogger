package com.weatherlogger.weather.current

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.weatherlogger.R
import com.weatherlogger.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.IOException
import java.util.*


class CurrentFragment : ScopedFragment(), KodeinAware {
    private var cityName = "Delhi"
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentViewModelFactory by instance()
    private lateinit var viewModel: CurrentViewModel
    private lateinit var adapter: MainWeatherAdapter
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = this.requireContext()
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentViewModel::class.java)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        getLastLocation()
    }

    private fun getLastLocation() {

        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this.requireActivity()) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        getCityName(location)
                    }
                }
            } else {
                Toast.makeText(mContext, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun getCityName(location: Location) {
        // Toast.makeText(mContext, "${location.latitude}", Toast.LENGTH_LONG).show()
        try {
            val geocoder = Geocoder(mContext, Locale.getDefault())
            val addresses: List<Address> =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            cityName = addresses[0].locality
        }catch (e: IOException){
            // when grpc fails
        }
        bindUI()
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            getCityName(mLastLocation)
            Toast.makeText(mContext, "${mLastLocation.latitude}", Toast.LENGTH_LONG).show()
            //   findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            // findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun bindUI() = launch {

        val mainWeather = viewModel.getWeatherData(cityName)
        mainWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            } else {
                val linearLayoutManager = LinearLayoutManager(this@CurrentFragment.context)
                rv_weather_data!!.layoutManager = linearLayoutManager
                adapter = MainWeatherAdapter(it, cityName)
                rv_weather_data.adapter = adapter
            }

        })
    }

}



