package com.hfad.worldskillsbank.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.fragments.*
import com.hfad.worldskillsbank.models.ModelToken
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        val PASSWORD_TITLE = "PASSWORD"
    }

    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
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
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            navFt.commit()
            true
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        if (supportFragmentManager.fragments.first() is HistoryFragment)
            menuInflater.inflate(R.menu.menu_search, menu)
        else menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, ProfileFragment())
        ft.addToBackStack("PROFILE")
        ft.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments.first()
        if (currentFragment is HomeFragment ||
                currentFragment is PaymentsFragment ||
                currentFragment is HistoryFragment ||
                currentFragment is DialogsFragment) {

            AlertDialog.Builder(this)
                    .setMessage(R.string.logout_title)
                    .setPositiveButton(R.string.logout) { _: DialogInterface, _: Int ->
                        App.logout(ModelToken(App.TOKEN), this)
                    }
                    .setNegativeButton(R.string.login_cancel, null)
                    .create().show()
        }
        else super.onBackPressed()
    }
}