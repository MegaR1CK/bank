package com.hfad.worldskillsbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bankomats.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BankomatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bankomats)

        recycler_bankomats.layoutManager = LinearLayoutManager(this)
        recycler_bankomats.adapter = BankomatsAdapter(listOf())

        App.MAIN_API.getBankomats().enqueue(object : Callback<List<Bankomat>> {
            override fun onResponse(call: Call<List<Bankomat>>, response: Response<List<Bankomat>>) {
                if (response.isSuccessful)
                    recycler_bankomats.adapter = response.body()?.let { BankomatsAdapter(it) }
            }

            override fun onFailure(call: Call<List<Bankomat>>, t: Throwable) {}
        })
    }
}