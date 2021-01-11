package com.hfad.worldskillsbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    companion object { val TOKEN_TITLE = "TOKEN"}

    // TODO: Вынести токен в app, сохранить аккаунт при закрытии
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        token = intent.extras?.getString(TOKEN_TITLE).toString()
        setSupportActionBar(toolbar as Toolbar)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, HomeFragment())
        ft.commit()

        bottom_nav_view.setOnNavigationItemSelectedListener {
            val navFt = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.bottom_nav_home -> navFt.replace(R.id.fragment_container, HomeFragment())
                R.id.bottom_nav_payments -> navFt.replace(R.id.fragment_container, PaymentsFragment())
                R.id.bottom_nav_history -> navFt.replace(R.id.fragment_container, HistoryFragment())
                R.id.bottom_nav_dialogs -> navFt.replace(R.id.fragment_container, DialogsFragment())
            }
            navFt.addToBackStack(null)
            navFt.commit()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout_menu -> {
                App.MAIN_API.logout(ModelToken(token)).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        val preferences = PreferenceManager
                            .getDefaultSharedPreferences(this@HomeActivity)
                        val editor = preferences.edit()
                        editor.clear()
                        editor.apply()
                        this@HomeActivity.finish()
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {}
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }
}