package com.hfad.worldskillsbank.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.hfad.worldskillsbank.*
import com.hfad.worldskillsbank.fragments.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        val TOKEN_TITLE = "TOKEN"
        val PASSWORD_TITLE = "PASSWORD"
    }

    lateinit var token: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        token = intent.extras?.getString(TOKEN_TITLE).toString()
        password = intent.extras?.getString(PASSWORD_TITLE).toString()
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
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            navFt.commit()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        val prevFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        prevFragment?.let { ProfileFragment(it) }?.let { ft.replace(R.id.fragment_container, it) }
        ft.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onOptionsItemSelected(item)
    }
}