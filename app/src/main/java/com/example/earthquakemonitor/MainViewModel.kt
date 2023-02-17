package com.example.earthquakemonitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
      get() = _eqList

    init {
       viewModelScope.launch {
            _eqList.value = fetchEarthquakes()
        }
    }

   private suspend fun fetchEarthquakes(): MutableList<Earthquake> {
      return withContext(Dispatchers.IO) {
          val eqList = mutableListOf<Earthquake>()
          eqList.add(Earthquake("1", "Buenos Aires", 4.3, 23243432L, -102.4567, 28.54353))
          eqList.add(Earthquake("2", "Lima", 2.9, 23243432L, -102.4567, 28.54353))
          eqList.add(Earthquake("3", "Cuidad de Mexico", 6.0, 23243432L, -102.4567, 28.54353))
          eqList.add(Earthquake("4", "Bogotá", 1.8, 23243432L, -102.4567, 28.54353))
          eqList.add(Earthquake("5", "Caracas", 3.5, 23243432L, -102.4567, 28.54353))
          eqList.add(Earthquake("6", "Madrid", 0.6, 23243432L, -102.4567, 28.54353))
          eqList.add(Earthquake("7", "Acra", 5.1, 23243432L, -102.4567, 28.54353))

          eqList
      }
    }


}

//Corrutinas: Procesos a los que podemos indicar en que hilo queremos que se ejecuten