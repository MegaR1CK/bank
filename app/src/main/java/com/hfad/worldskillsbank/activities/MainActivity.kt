package com.hfad.worldskillsbank.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.dialogs.LoginDialog
import com.hfad.worldskillsbank.models.ModelLogin
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.models.ModelValCurs
import com.hfad.worldskillsbank.other.UserData
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

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (preferences.contains("TOKEN")) {
            App.WAS_AUTHORIZED = true
            App.USER = UserData(preferences.getString("TOKEN", null).toString())
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(HomeActivity.PASSWORD_TITLE,
                    preferences.getString("PASSWORD", null))
            startActivity(intent)
        }
        else App.WAS_AUTHORIZED = false

        setContentView(R.layout.activity_main)

        valute_btn.btn_date.text = SimpleDateFormat("dd.MM.yyyy",
            Locale.getDefault()).format(Date())

        valute_btn.setOnClickListener(this)
        bankomats_btn.setOnClickListener(this)
        login_btn.setOnClickListener(this)

        App.XML_API.getValutes().enqueue(object : Callback<ModelValCurs> {
            override fun onResponse(call: Call<ModelValCurs>, response: Response<ModelValCurs>) {
                if (response.isSuccessful) {
                    val dollar = response.body()?.valutes?.find { it.CharCode == "USD" }
                    val euro = response.body()?.valutes?.find { it.CharCode == "EUR" }
                    valute_btn.val1_code.text = dollar?.CharCode
                    valute_btn.val1_value.text = dollar?.Value
                    valute_btn.val2_code.text = euro?.CharCode
                    valute_btn.val2_value.text = euro?.Value
                }
                else App.errorAlert(response.message(), this@MainActivity)
            }
            override fun onFailure(call: Call<ModelValCurs>, t: Throwable) {
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
                dialog.loginListener = object : LoginDialog.LoginListener {
                    override fun loginFromActivity(model: ModelLogin) {
                        login_pb.visibility = View.VISIBLE
                        darken_layout.visibility = View.VISIBLE
                        App.MAIN_API.login(model).enqueue(object : Callback<ModelToken> {
                            override fun onResponse(call: Call<ModelToken>,
                                                    response: Response<ModelToken>) {
                                if (!response.isSuccessful)
                                    App.errorAlert(response.message(), this@MainActivity)
                                else {
                                    val token = response.body()?.token
                                    val preferences = PreferenceManager
                                        .getDefaultSharedPreferences(this@MainActivity)
                                    val editor = preferences.edit()
                                    editor.putString("TOKEN", token)
                                    editor.putString("PASSWORD", model.password)
                                    editor.apply()
                                    App.USER = token?.let { it1 -> UserData(it1) }
                                    login_pb.visibility = View.INVISIBLE
                                    darken_layout.visibility = View.INVISIBLE
                                    val intent = Intent(this@MainActivity,
                                        HomeActivity::class.java)
                                    intent.putExtra(HomeActivity.PASSWORD_TITLE, model.password)
                                    startActivity(intent)
                                }
                            }
                            override fun onFailure(call: Call<ModelToken>, t: Throwable) {
                                t.message?.let { it1 -> Toast.makeText(this@MainActivity,
                                    it1, Toast.LENGTH_SHORT).show() }
                            }
                        })
                    }
                }
                val ft = supportFragmentManager.beginTransaction()
                dialog.show(ft, "login")
            }
        }
    }
}