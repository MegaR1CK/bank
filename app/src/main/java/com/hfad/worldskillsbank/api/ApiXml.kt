package com.hfad.worldskillsbank.api

import com.hfad.worldskillsbank.models.ModelValCurs
import retrofit2.Call
import retrofit2.http.GET

interface ApiXml {
    @GET("XML_daily.asp")
    fun getValutes(): Call<ModelValCurs>
}