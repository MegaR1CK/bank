package com.hfad.worldskillsbank

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.hfad.worldskillsbank.activities.MainActivity
import com.hfad.worldskillsbank.api.ApiGeocoder
import com.hfad.worldskillsbank.api.ApiMain
import com.hfad.worldskillsbank.api.ApiXml
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelCredit
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.other.UserData
import com.yandex.mapkit.MapKitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

//TODO: Уведомления при платежах
//TODO: обработка блокированных карт
//TODO: локализация

class App : Application() {
    companion object {
        var WAS_AUTHORIZED = false

        var USER: UserData? = null

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
                    if (response.isSuccessful) {
                        val preferences = PreferenceManager
                            .getDefaultSharedPreferences(activity)
                        val editor = preferences.edit()
                        editor.clear()
                        editor.apply()
                        USER = null
                        if (WAS_AUTHORIZED)
                            activity.startActivity(Intent(activity, MainActivity::class.java))
                        activity.finish()
                    }
                    else errorAlert(response.message(), activity)
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.message?.let { errorAlert(it, activity) }
                }
            })
        }

        fun errorAlert(text: String, context: Context) {
            AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(text)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show()
        }
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("ed98a8e0-bbb2-42e6-b8d4-0d4810e692e0")
    }
}