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
import com.example.earthquakemonitor.repository.ApiResponseStatus
import com.example.earthquakemonitor.repository.MainRepository
import kotlinx.coroutines.*
import java.net.UnknownHostException

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status

    val eqList = repository.eqList

    init {
       viewModelScope.launch {
           try {
                _status.value = ApiResponseStatus.LOADING
                repository.fetchEarthquakes()
               _status.value = ApiResponseStatus.DONE
           } catch (e: UnknownHostException) {
               _status.value = ApiResponseStatus.NOT_INTERNET
               Log.d(TAG, "No internet connection", e)
           }
       }
    }




}

//Corrutinas: Procesos a los que podemos indicar en que hilo queremos que se ejecuten