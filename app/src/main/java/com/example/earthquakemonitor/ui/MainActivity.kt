package com.example.earthquakemonitor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.ui.adapter.EqAdapter
import com.example.earthquakemonitor.presentation.MainViewModel
import com.example.earthquakemonitor.databinding.ActivityMainBinding
import com.example.earthquakemonitor.presentation.MainViewModelFactory
import com.example.earthquakemonitor.repository.ApiResponseStatus

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)


        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter //Asignamos el adaptar al recyclerView

        adapter.onItemClickListener = {
            openDetailActivity(it)
        }


        viewModel.eqList.observe(this, Observer { eqList ->
            adapter.submitList(eqList) //Pasamos la lista de terremotos al adapter

            handleEmptyView(eqList, binding)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus ->
            if(apiResponseStatus == ApiResponseStatus.LOADING){
                binding.loadingWheel.visibility = View.VISIBLE
            } else if (apiResponseStatus == ApiResponseStatus.DONE){
               binding.loadingWheel.visibility = View.GONE
            } else if (apiResponseStatus == ApiResponseStatus.NOT_INTERNET){
                binding.loadingWheel.visibility = View.GONE
            }
         })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmptyView(eqList: MutableList<Earthquake>, binding: ActivityMainBinding) {

        if (eqList.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }

    private fun openDetailActivity(earthquake: Earthquake) {
        val intent = Intent(this, EqDetailActivity::class.java)
        intent.putExtra(EqDetailActivity.EQ_KEY, earthquake)
        startActivity(intent)
    }
}