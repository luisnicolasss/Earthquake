package com.example.earthquakemonitor.presentation

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.database.getDatabase
import com.example.earthquakemonitor.repository.MainRepository
import kotlinx.coroutines.*
import java.net.UnknownHostException

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
      get() = _eqList

    private val database = getDatabase(application)
    val repository = MainRepository(database)

    init {
       viewModelScope.launch {
           try {
               _eqList.value = repository.fetchEarthquakes()
           } catch (e: UnknownHostException) {
               Log.d(TAG, "No internet connection", e)
           }
       }
    }




}

//Corrutinas: Procesos a los que podemos indicar en que hilo queremos que se ejecuten