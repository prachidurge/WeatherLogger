package com.weatherlogger.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.weatherlogger.data.WeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "694b1dfad175bce01244a21c5ac3bfef"
interface WeatherAPIService {
//sample: http://api.openweathermap.org/data/2.5/weather?q=Vadodara&appid=694b1dfad175bce01244a21c5ac3bfef
    @GET("weather")
    fun getWeatherData(
        @Query("q") city:String
    ): Deferred<WeatherResponse>  // part of corountine

    companion object{
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor

        ): WeatherAPIService {
          // return WeatherAPIService()
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url().newBuilder().addQueryParameter("appid",
                        API_KEY
                    )
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder().client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIService::class.java)

        }
    }
}