package com.mayank.kotlinmvvm.data

class MainRepository constructor (private val retrofitService: RetrofitService) {

    fun getAllPhotos() = retrofitService.getAllPhotos()
}