package com.hfad.worldskillsbank

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LoginDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder.setTitle(getString(R.string.auth_title))
                .setView(R.layout.dialog_login)
                .setPositiveButton(getString(R.string.login_log), null)
                .setNegativeButton(getString(R.string.login_cancel), null)
                .create()
    }
}