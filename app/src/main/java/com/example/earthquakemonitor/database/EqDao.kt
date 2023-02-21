package com.example.earthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.earthquakemonitor.Earthquake

@Dao //DATA ACCESS OBJECT
interface EqDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( eqList: MutableList<Earthquake>) //Insertamos lo terremotos

    @Query("select * from earthquakes")
    fun getEarthquakes(): LiveData<MutableList<Earthquake>> //Obtenemos los terremotos



}