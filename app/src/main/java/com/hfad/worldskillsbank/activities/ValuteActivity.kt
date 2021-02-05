package com.hfad.worldskillsbank.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelValCurs
import com.hfad.worldskillsbank.adapters.ValuteAdapter
import kotlinx.android.synthetic.main.activity_valute.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ValuteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valute)

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = sdf.format(Date())
        val_date.text = date

        recycler_valutes.layoutManager = LinearLayoutManager(this)
        recycler_valutes.adapter = ValuteAdapter(listOf())

        App.XML_API.getValutes().enqueue(object : Callback<ModelValCurs> {
            override fun onResponse(call: Call<ModelValCurs>, response: Response<ModelValCurs>) {
                if (response.isSuccessful)
                    recycler_valutes.adapter = response.body()?.valutes?.let { ValuteAdapter(it) }
                else App.errorAlert(response.message(), this@ValuteActivity)
            }

            override fun onFailure(call: Call<ModelValCurs>, t: Throwable) {
                t.message?.let { Log.d("APP", it) }
            }
        })
    }
}