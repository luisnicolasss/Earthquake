package com.example.earthquakemonitor.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.repository.MainRepository
import kotlinx.coroutines.*

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