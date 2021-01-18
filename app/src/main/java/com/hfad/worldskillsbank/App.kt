package com.hfad.worldskillsbank

import android.app.Application
import com.hfad.worldskillsbank.api.ApiMain
import com.hfad.worldskillsbank.api.ApiXml
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class App : Application() {
    companion object {
        var WAS_AUTHORIZED = false

        private val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:57905/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        private val retrofit2: Retrofit = Retrofit.Builder()
                .baseUrl("http://www.cbr.ru/scripts/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()

        val MAIN_API: ApiMain = retrofit.create(ApiMain::class.java)

        val XML_API: ApiXml = retrofit2.create(ApiXml::class.java)
    }
}