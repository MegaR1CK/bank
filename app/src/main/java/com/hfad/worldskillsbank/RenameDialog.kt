package com.hfad.worldskillsbank

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_one_field.*
import kotlinx.android.synthetic.main.fragment_card_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RenameDialog(val card: ModelCard) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder
            .setTitle(getString(R.string.card_rename_title))
            .setView(R.layout.dialog_one_field)
            .setPositiveButton(getString(R.string.rename_pos_btn), null)
            .setNegativeButton(getString(R.string.login_cancel), null)
            .create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog

        dialog.edit_desc.text = getString(R.string.rename_desc)
        dialog.edit_field.hint = getString(R.string.card_rename_hint)

        val button = dialog.getButton(Dialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            if (dialog.edit_field.text.isNotBlank()) {
                App.MAIN_API.renameCard(ModelRenameCard(
                    (activity as HomeActivity).token,
                    card.cardNumber,
                    dialog.edit_field.text.toString()))
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Toast.makeText(activity, getString(R.string.card_rename_success),
                                Toast.LENGTH_SHORT).show()

                            dialog.dismiss()
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {}
                    })
            }
            else Toast.makeText(activity, getString(R.string.card_rename_short_name),
                Toast.LENGTH_SHORT).show()
        }
    }
}