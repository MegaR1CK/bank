package com.hfad.worldskillsbank

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.preference.PreferenceManager
import com.hfad.worldskillsbank.activities.MainActivity
import com.hfad.worldskillsbank.api.ApiGeocoder
import com.hfad.worldskillsbank.api.ApiMain
import com.hfad.worldskillsbank.api.ApiXml
import com.hfad.worldskillsbank.models.ModelToken
import com.yandex.mapkit.MapKitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

//TODO: Уведомления при платежах
//TODO: загрузка spinner
//TODO: обработка блокированных карт
//TODO: запрос к картам только при запуске и платежах, хранение списка в App
//TODO: обработка входа с нескольких устройств
class App : Application() {
    companion object {
        var WAS_AUTHORIZED = false
        lateinit var TOKEN: String

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://ws-bank.somee.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val retrofit2: Retrofit = Retrofit.Builder()
            .baseUrl("http://www.cbr.ru/scripts/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        private val retrofit3: Retrofit = Retrofit.Builder()
            .baseUrl("https://geocode-maps.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val MAIN_API: ApiMain = retrofit.create(ApiMain::class.java)

        val XML_API: ApiXml = retrofit2.create(ApiXml::class.java)

        val GEO_API: ApiGeocoder = retrofit3.create(ApiGeocoder::class.java)

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

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("ed98a8e0-bbb2-42e6-b8d4-0d4810e692e0")
    }
}