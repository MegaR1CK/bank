package com.hfad.worldskillsbank.api

import com.hfad.worldskillsbank.models.GeocodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGeocoder {

    @GET("1.x")
    fun geocode(@Query("apikey") key: String,
                @Query("geocode") address: String,
                @Query("format") format: String) : Call<GeocodeResponse>
}