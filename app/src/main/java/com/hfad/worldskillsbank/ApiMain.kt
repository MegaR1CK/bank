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

    @POST("getuser")
    fun getUser(@Body token: ModelToken): Call<ModelUser>

    @POST("getcards")
    fun getCards(@Body token: ModelToken): Call<List<ModelCard>>

    @POST("getchecks")
    fun getChecks(@Body token: ModelToken): Call<List<ModelCheck>>

    @POST("getcredits")
    fun getCredits(@Body token: ModelToken): Call<List<ModelCredit>>

    @PUT("editusername")
    fun editUsername(@Body editUsernameModel: ModelEditUsername): Call<Void>

    @PUT("editpassword")
    fun editPassword(@Body editPasswordModel: ModelEditPassword): Call<Void>

    @POST("lastlogin")
    fun getLastLogin(@Body token: ModelToken): Call<List<ModelLastLogin>>

    @POST("block")
    fun blockCard(@Body cardBlockModel: ModelCardBlock): Call<Void>
}