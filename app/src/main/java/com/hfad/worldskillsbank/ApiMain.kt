package com.hfad.worldskillsbank

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiMain {
    @GET("bankomats")
    fun getBankomats(): Call<List<Bankomat>>

    @POST("login")
    fun login(@Body logModel: ModelLogin): Call<ModelToken>
}