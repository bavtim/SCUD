package com.example.scud

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json")
    @POST("reservation/available")
    suspend fun getEmployees(@Body userData: HashMap<String?, String?>): Response<MutableList<ClassInfo>>
}