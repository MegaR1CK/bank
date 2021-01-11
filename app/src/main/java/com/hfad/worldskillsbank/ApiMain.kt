package com.hfad.worldskillsbank

import retrofit2.Call
import retrofit2.http.*

interface ApiMain {
    @GET("bankomats")
    fun getBankomats(): Call<List<Bankomat>>

    @POST("login")
    fun login(@Body logModel: ModelLogin): Call<ModelToken>

    @HTTP(method = "DELETE", path = "logout", hasBody = true)
    fun logout(@Body token: ModelToken): Call<Void>
}