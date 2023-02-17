package com.example.earthquakemonitor

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface EqApiService {
  @GET("all_hour.geojson")
  suspend fun getLastHourEarthquakes(): String
}

var retrofit = Retrofit.Builder()
  .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
  .addConverterFactory(ScalarsConverterFactory.create()) //Esto convierte los datos una vez descargados a tipo String
  .build()

var service: EqApiService = retrofit.create(EqApiService::class.java)