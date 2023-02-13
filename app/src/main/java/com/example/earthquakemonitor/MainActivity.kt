package com.example.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val eqList = mutableListOf<Earthquake>()
        eqList.add(Earthquake("1", "Buenos Aires", 4.3, 23243432L, -102.4567, 28.54353))
        eqList.add(Earthquake("2", "Lima", 2.9, 23243432L, -102.4567, 28.54353))
        eqList.add(Earthquake("3", "Cuidad de Mexico", 6.0, 23243432L, -102.4567, 28.54353))
        eqList.add(Earthquake("4", "Bogotá", 1.8, 23243432L, -102.4567, 28.54353))
        eqList.add(Earthquake("5", "Caracas", 3.5, 23243432L, -102.4567, 28.54353))
        eqList.add(Earthquake("6", "Madrid", 0.6, 23243432L, -102.4567, 28.54353))
        eqList.add(Earthquake("7", "Acra", 5.1, 23243432L, -102.4567, 28.54353))

        val adapter = EqAdapter()
        binding.eqRecycler.adapter = adapter //Asignamos el adaptar al recyclerView
        adapter.submitList(eqList) //Pasamos la lista de terremotos al adapter

        if(eqList.isEmpty()){
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }
}