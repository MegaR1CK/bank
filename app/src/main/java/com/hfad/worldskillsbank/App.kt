package com.hfad.worldskillsbank

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.preference.PreferenceManager
import com.hfad.worldskillsbank.activities.MainActivity
import com.hfad.worldskillsbank.api.ApiMain
import com.hfad.worldskillsbank.api.ApiXml
import com.hfad.worldskillsbank.models.ModelToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class App : Application() {
    companion object {
        var WAS_AUTHORIZED = false
        lateinit var TOKEN: String

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

        fun logout(token: ModelToken, activity: Activity) {
            MAIN_API.logout(token).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    val preferences = PreferenceManager
                            .getDefaultSharedPreferences(activity)
                    val editor = preferences.edit()
                    editor.clear()
                    editor.apply()
                    if (WAS_AUTHORIZED)
                        activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {}
            })
        }
    }
}