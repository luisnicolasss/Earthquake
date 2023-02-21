package com.example.earthquakemonitor.repository

import androidx.lifecycle.LiveData
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository(private val database: EqDatabase) {

    val eqList: LiveData<MutableList<Earthquake>> = database.eqDao.getEarthquakes()

    suspend fun fetchEarthquakes() {
        return withContext(Dispatchers.IO) {
            val eqListString = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqListString)

            database.eqDao.insertAll(eqList) //Guardamos los datos en la base de datos


        }
    }

    private fun parseEqResult(eqListString: String): MutableList<Earthquake> {
        val eqJsonObject = JSONObject(eqListString)
        val featuresJsonArray = eqJsonObject.getJSONArray("features") //Obtenemos el array features

        val eqList = mutableListOf<Earthquake>()

        for (i in 0 until featuresJsonArray.length()) {
            val featuresJsonObject = featuresJsonArray[i] as JSONObject
            val id = featuresJsonObject.getString("id")

            val propertiesJsonObject = featuresJsonObject.getJSONObject("properties")
            val magnitude = propertiesJsonObject.getDouble("mag")
            val place = propertiesJsonObject.getString("place")
            val time = propertiesJsonObject.getLong("time")

            val geometryJsonObject = featuresJsonObject.getJSONObject("geometry")
            val coordinatesJsonArray = geometryJsonObject.getJSONArray("coordinates")
            val longitude = coordinatesJsonArray.getDouble(0)
            val latitude = coordinatesJsonArray.getDouble(1)

            val earthquake = Earthquake(id, place, magnitude, time, longitude, latitude)
            eqList.add(earthquake)
        }
        return eqList
    }
}