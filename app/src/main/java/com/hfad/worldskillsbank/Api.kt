package com.hfad.worldskillsbank

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("bankomats.json")
    fun getBankomats(): Call<List<Bankomat>>
}