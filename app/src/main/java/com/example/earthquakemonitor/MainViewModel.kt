package com.example.earthquakemonitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.json.JSONObject

class MainViewModel: ViewModel() {

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
      get() = _eqList

    val repository = MainRepository()

    init {
       viewModelScope.launch {
            _eqList.value = repository.fetchEarthquakes()
        }
    }




}

//Corrutinas: Procesos a los que podemos indicar en que hilo queremos que se ejecuten