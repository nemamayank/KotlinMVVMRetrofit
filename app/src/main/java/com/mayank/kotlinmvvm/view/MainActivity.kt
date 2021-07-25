package com.mayank.kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mayank.kotlinmvvm.*
import com.mayank.kotlinmvvm.viewmodel.MainViewModel
import com.mayank.kotlinmvvm.viewmodel.ViewModelFactory
import com.mayank.kotlinmvvm.databinding.ActivityMainBinding
import com.mayank.kotlinmvvm.data.MainRepository
import com.mayank.kotlinmvvm.data.RetrofitService
import com.mayank.kotlinmvvm.utils.ConnectivityLiveData

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.qualifiedName

    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val listAdapter = PhotoListAdapter()
    private lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initObservers()
    }

    private fun initObservers() {

        connectivityLiveData.observe(this, { isConnected ->
            when (isConnected) {
                true -> {
                    fetchPhotoList()
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.viewNoInternet.root.visibility = View.GONE
                }

                false -> {
                    binding.recyclerview.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.viewNoInternet.root.visibility = View.VISIBLE
                }
            }
        })

        viewModel.photoList.observe(this, {
            Log.d(TAG, "onCreate: $it")
            binding.progressBar.visibility = View.GONE
            listAdapter.setPhotoList(it)
        })

        viewModel.errorMessage.observe(this, {
            Log.d(TAG, "onError: $it")
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
    }

    private fun initComponents() {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectivityLiveData = ConnectivityLiveData(application)

        viewModel = ViewModelProvider(this,
                        ViewModelFactory(MainRepository(retrofitService)))
                            .get(MainViewModel::class.java)

        binding.recyclerview.adapter = listAdapter

        binding.viewNoInternet.root.visibility = View.VISIBLE
    }

    private fun fetchPhotoList() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getAllPhoto()
    }
}