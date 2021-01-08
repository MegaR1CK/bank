package com.hfad.worldskillsbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
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

        App.XML_API.getValutes().enqueue(object : Callback<ValCurs> {
            override fun onResponse(call: Call<ValCurs>, response: Response<ValCurs>) {
                recycler_valutes.adapter = response.body()?.valutes?.let { ValuteAdapter(it) }
            }

            override fun onFailure(call: Call<ValCurs>, t: Throwable) {
                t.message?.let { Log.d("APP", it) }
            }
        })
    }
}