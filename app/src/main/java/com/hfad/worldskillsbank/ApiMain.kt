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
    fun blockCard(@Body cardPostModel: ModelCardPost): Call<Void>

    @POST("history/card")
    fun getCardTransactions(@Body cardPostModel: ModelCardPost): Call<List<ModelTransaction>>

    @PUT("rename/card")
    fun renameCard(@Body renameCard: ModelRenameCard): Call<Void>

    @POST("history/check")
    fun getCheckTransactions(@Body checkPostModel: ModelCheckPost): Call<List<ModelTransaction>>

    @PUT("rename/check")
    fun renameCheck(@Body renameCheck: ModelRenameCheck): Call<Void>

    @POST("dotransaction")
    fun doTransaction(@Body transactionPost: ModelTransactionPost): Call<Void>

    @POST("history/all")
    fun getTransactions(@Body token: ModelToken): Call<List<ModelTransaction>>
}