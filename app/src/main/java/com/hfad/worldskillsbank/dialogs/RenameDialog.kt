package com.hfad.worldskillsbank.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelRenameCard
import com.hfad.worldskillsbank.models.ModelRenameCheck
import kotlinx.android.synthetic.main.dialog_one_field.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RenameDialog<T>(private val item: T) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder
            .setView(R.layout.dialog_one_field)
            .setPositiveButton(getString(R.string.rename_pos_btn), null)
            .setNegativeButton(getString(R.string.login_cancel), null)
        if (item is ModelCard)
            builder.setTitle(getString(R.string.card_rename_title))
        else
            builder.setTitle(getString(R.string.check_rename_title))
        return builder.create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog

        dialog.edit_desc.text = getString(R.string.rename_desc)
        if (item is ModelCard)
            dialog.edit_field.hint = getString(R.string.card_rename_hint)
        else
            dialog.edit_field.hint = getString(R.string.check_rename_hint)

        val button = dialog.getButton(Dialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            if (dialog.edit_field.text.isNotBlank()) {
                if (item is ModelCard) {
                    App.MAIN_API.renameCard(ModelRenameCard(
                        App.TOKEN,
                        item.cardNumber,
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
                if (item is ModelCheck) {
                    App.MAIN_API.renameCheck(ModelRenameCheck(
                        App.TOKEN,
                        item.checkNumber,
                        dialog.edit_field.text.toString()))
                        .enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                Toast.makeText(activity, getString(R.string.check_rename_success),
                                    Toast.LENGTH_SHORT).show()

                                dialog.dismiss()
                            }
                            override fun onFailure(call: Call<Void>, t: Throwable) {}
                        })
                }

            }
            else Toast.makeText(activity, getString(R.string.card_rename_short_name),
                Toast.LENGTH_SHORT).show()
        }
    }
}