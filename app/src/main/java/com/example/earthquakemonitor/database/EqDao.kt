package com.example.earthquakemonitor.database

import androidx.room.*
import com.example.earthquakemonitor.Earthquake

@Dao //DATA ACCESS OBJECT
interface EqDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>) //Insertamos lo terremotos

    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes(): MutableList<Earthquake> //Obtenemos los terremotos



}