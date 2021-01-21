package com.hfad.worldskillsbank.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelLogin
import com.hfad.worldskillsbank.models.ModelToken
import kotlinx.android.synthetic.main.dialog_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder.setTitle(getString(R.string.auth_title))
                .setView(R.layout.dialog_login)
                .setPositiveButton(getString(R.string.login_log), null)
                .setNegativeButton(getString(R.string.login_cancel), null)
                .create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        val button = dialog.getButton(Dialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            val username = dialog.field_username.text.toString()
            val password = dialog.field_password.text.toString()
            App.MAIN_API.login(ModelLogin(username, password)).enqueue(object : Callback<ModelToken> {
                override fun onResponse(call: Call<ModelToken>, response: Response<ModelToken>) {
                    if (!response.isSuccessful)
                        Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show()
                    else {
                        val resp = response.body()?.token
                        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                        val editor = preferences.edit()
                        editor.putString("TOKEN", resp)
                        editor.putString("PASSWORD", password)
                        editor.apply()
                        if (resp != null) { App.TOKEN = resp }
                        val intent = Intent(activity, HomeActivity::class.java)
                        intent.putExtra(HomeActivity.PASSWORD_TITLE, password)
                        dialog.dismiss()
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ModelToken>, t: Throwable) {
                    t.message?.let { it1 -> Toast.makeText(activity, it1, Toast.LENGTH_SHORT).show() }
                }
            })
        }
    }
}