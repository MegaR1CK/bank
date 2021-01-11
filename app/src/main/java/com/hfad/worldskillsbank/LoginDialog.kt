package com.hfad.worldskillsbank

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
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
                    val resp = response.body()?.token
                    if (resp?.length != 25)
                        Toast.makeText(activity, resp, Toast.LENGTH_SHORT).show()
                    else {
                        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
                        val editor = preferences.edit()
                        editor.putString("TOKEN", resp)
                        editor.apply()
                        val intent = Intent(activity, HomeActivity::class.java)
                        intent.putExtra(HomeActivity.TOKEN_TITLE, resp)
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