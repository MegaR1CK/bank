package com.hfad.worldskillsbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.button_valute.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (preferences.contains("TOKEN")) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(HomeActivity.TOKEN_TITLE, preferences.getString("TOKEN", null))
            startActivity(intent)
        }

        valute_btn.btn_date.text = SimpleDateFormat("dd.MM.yyyy",
            Locale.getDefault()).format(Date())

        valute_btn.setOnClickListener(this)
        bankomats_btn.setOnClickListener(this)
        login_btn.setOnClickListener(this)

        App.XML_API.getValutes().enqueue(object : Callback<ValCurs> {
            override fun onResponse(call: Call<ValCurs>, response: Response<ValCurs>) {
                val dollar = response.body()?.valutes?.find { it.CharCode == "USD" }
                val euro = response.body()?.valutes?.find { it.CharCode == "EUR" }
                valute_btn.val1_code.text = dollar?.CharCode
                valute_btn.val1_value.text = dollar?.Value
                valute_btn.val2_code.text = euro?.CharCode
                valute_btn.val2_value.text = euro?.Value
            }
            override fun onFailure(call: Call<ValCurs>, t: Throwable) {
                t.message?.let { Log.d("APP", it) }
                valute_btn.val1_code.text = resources.getString(R.string.not_found)
                valute_btn.val2_code.text = resources.getString(R.string.not_found)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.valute_btn ->
                startActivity(Intent(this, ValuteActivity::class.java))

            R.id.bankomats_btn ->
                startActivity(Intent(this, BankomatsActivity::class.java))

            R.id.login_btn -> {
                val dialog = LoginDialog()
                val ft = supportFragmentManager.beginTransaction()
                dialog.show(ft, "login")
            }
        }
    }
}