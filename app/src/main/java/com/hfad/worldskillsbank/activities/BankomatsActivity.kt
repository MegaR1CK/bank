package com.hfad.worldskillsbank.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.*
import com.hfad.worldskillsbank.adapters.BankomatsAdapter
import com.hfad.worldskillsbank.models.ModelBankomat
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

        App.MAIN_API.getBankomats().enqueue(object : Callback<List<ModelBankomat>> {
            override fun onResponse(call: Call<List<ModelBankomat>>, response: Response<List<ModelBankomat>>) {
                if (response.isSuccessful)
                    recycler_bankomats.adapter = response.body()?.let { BankomatsAdapter(it) }
            }

            override fun onFailure(call: Call<List<ModelBankomat>>, t: Throwable) {}
        })
    }
}