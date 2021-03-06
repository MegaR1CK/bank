package com.hfad.worldskillsbank.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelEditPassword
import com.hfad.worldskillsbank.models.ModelEditUsername
import kotlinx.android.synthetic.main.dialog_one_field.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

class EditLoginDialog(val editType: EditType) : DialogFragment() {

    private var mContext: Context? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return if (editType == EditType.EditUsername) {
            builder.setTitle(R.string.username_edit_title)
                    .setView(R.layout.dialog_one_field)
                    .setPositiveButton(R.string.edit_button, null)
                    .setNegativeButton(R.string.login_cancel, null)
                    .create()
        }
        else {
            builder.setTitle(R.string.password_edit_title)
                    .setView(R.layout.dialog_one_field)
                    .setPositiveButton(R.string.edit_button, null)
                    .setNegativeButton(R.string.login_cancel, null)
                    .create()
        }
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        if (editType == EditType.EditUsername) {
            dialog.edit_desc.text = getString(R.string.username_edit_desc)
            dialog.edit_field.hint = getString(R.string.uname)
        }
        else {
            dialog.edit_desc.text = getString(R.string.password_edit_desc)
            dialog.edit_field.hint = getString(R.string.password)
            dialog.edit_field.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            if (editType == EditType.EditPassword && (dialog.edit_field.text.isBlank() ||
                            dialog.edit_field.text.length < 6))
                Toast.makeText(mContext, getString(R.string.short_password), Toast.LENGTH_SHORT).show()
            else {
                if (editType == EditType.EditUsername) {
                    App.MAIN_API.editUsername(ModelEditUsername(App.USER?.token ?: "",
                            dialog.edit_field.text.toString())).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                try {
                                    Toast.makeText(mContext, getString(R.string.username_changed),
                                        Toast.LENGTH_SHORT).show()
                                } catch (e: IllegalStateException) {
                                    Toast.makeText(activity, getString(R.string.username_changed),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                            else activity?.let { it1 -> App.errorAlert(response.message(), it1) }
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            activity?.let { it1 -> t.message?.let { it2 -> App.errorAlert(it2, it1) } }
                        }
                    })
                }
                else {
                    App.MAIN_API.editPassword(ModelEditPassword(App.USER?.token ?: "",
                            dialog.edit_field.text.toString())).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                try {
                                    Toast.makeText(mContext, getString(R.string.password_changed),
                                        Toast.LENGTH_SHORT).show()
                                }
                                catch (e: IllegalStateException) {
                                    Toast.makeText(activity, getString(R.string.password_changed),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                            else activity?.let { App.errorAlert(response.message(), it) }
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            t.message?.let { it1 -> activity?.let { it2 -> App.errorAlert(it1, it2) } }
                        }
                    })
                }
                dialog.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    enum class EditType { EditUsername, EditPassword }
}