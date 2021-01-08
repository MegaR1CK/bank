package com.hfad.worldskillsbank

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("bankomats.json")
    fun getBankomats(): Call<List<Bankomat>>
}