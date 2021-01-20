package com.hfad.worldskillsbank.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCardPost
import kotlinx.android.synthetic.main.dialog_one_field.*
import kotlinx.android.synthetic.main.fragment_card_info.*
import kotlinx.android.synthetic.main.item_card.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardBlockDialog(val card: ModelCard) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder.setTitle(getString(R.string.card_block_dialog_title))
                .setView(R.layout.dialog_one_field)
                .setPositiveButton(getString(R.string.card_block_pos_btn), null)
                .setNegativeButton(getString(R.string.login_cancel), null)
                .create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        dialog.edit_desc.text = getString(R.string.card_block_dialog_desc)
        dialog.edit_field.hint = getString(R.string.password)
        dialog.edit_field.transformationMethod = PasswordTransformationMethod.getInstance()

        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            if (dialog.edit_field.text.toString() == (activity as HomeActivity).password) {
                App.MAIN_API.blockCard(ModelCardPost(App.TOKEN,
                        card.cardNumber)).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        (activity as HomeActivity).card_info_block.visibility = View.VISIBLE
                        (activity as HomeActivity).card_info_list.visibility = View.INVISIBLE
                        (activity as HomeActivity).deposit_btn.isEnabled = false
                        (activity as HomeActivity).transfer_btn.isEnabled = false
                        dialog.dismiss()
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {}
                })
            }
            else
                Toast.makeText(activity, getString(R.string.wrong_password),
                        Toast.LENGTH_SHORT).show()
        }
    }
}