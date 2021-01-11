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

    @POST("getcards")
    fun getCards(@Body token: ModelToken): Call<List<ModelCard>>

    @POST("getchecks")
    fun getChecks(@Body token: ModelToken): Call<List<ModelCheck>>

    @POST("getcredits")
    fun getCredits(@Body token: ModelToken): Call<List<ModelCredit>>
}