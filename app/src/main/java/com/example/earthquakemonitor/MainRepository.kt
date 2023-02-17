package com.example.earthquakemonitor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainRepository {

     suspend fun fetchEarthquakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            val eqListString = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqListString)

            eqList
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
            val coordinatesJsonArray  = geometryJsonObject.getJSONArray("coordinates")
            val longitude = coordinatesJsonArray.getDouble(0)
            val latitude = coordinatesJsonArray.getDouble(1)

            val earthquake = Earthquake(id, place, magnitude, time, longitude, latitude)
            eqList.add(earthquake)
        }
        return eqList
    }
}