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
import com.hfad.worldskillsbank.other.UserData
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
            dialog.dismiss()
            loginListener.loginFromActivity(ModelLogin(username, password))
        }
    }

    lateinit var loginListener: LoginListener

    interface LoginListener {
        fun loginFromActivity(model: ModelLogin)
    }
}