package com.hfad.worldskillsbank

import retrofit2.Call
import retrofit2.http.GET

interface ApiXml {
    @GET("XML_daily.asp")
    fun getValutes(): Call<ValCurs>
}