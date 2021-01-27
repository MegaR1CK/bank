package com.hfad.worldskillsbank.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.fragments.HomeFragment
import com.hfad.worldskillsbank.models.ModelTransactionPost
import kotlinx.android.synthetic.main.dialog_one_field.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AcceptTransactionDialog(val numSource: String,
                              val numDest: String,
                              val sum: Double) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return builder.setTitle(getString(R.string.transaction_accept_title))
            .setView(R.layout.dialog_one_field)
            .setPositiveButton(R.string.transaction_accept_btn, null)
            .setNegativeButton(R.string.login_cancel, null)
            .create()
    }

    @ObsoleteCoroutinesApi
    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog

        dialog.edit_field.hint = getString(R.string.password)
        dialog.edit_desc.text = getString(R.string.transaction_accept_desc)
        dialog.edit_field.transformationMethod = PasswordTransformationMethod.getInstance()

        val currentDate = SimpleDateFormat("dd.MM.yyyy HH:mm",
            Locale.getDefault()).format(Date())

        val button = dialog.getButton(Dialog.BUTTON_POSITIVE)
        button.setOnClickListener {
            if (dialog.edit_field.text.toString() == (activity as HomeActivity).password) {
                App.MAIN_API.doTransaction(
                    ModelTransactionPost(App.TOKEN, numSource, numDest, currentDate, sum))
                    .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        val dialogSuccess = AlertDialog.Builder(activity)
                            .setMessage(R.string.transaction_success)
                            .setPositiveButton(R.string.ok) { dialog1: DialogInterface, _: Int ->
                                transactionSuccessListener.changeFragment(HomeFragment())
                                dialog1.dismiss()
                            }
                        dialogSuccess.create().show()
                        runBlocking(newSingleThreadContext("CARDS")) {
                            App.updateCardList()
                            App.updateCheckList()
                        }
                        dialog.dismiss()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {}

                })
            } else Toast.makeText(activity, R.string.wrong_password, Toast.LENGTH_SHORT).show()
        }
    }

    lateinit var transactionSuccessListener: TransactionSuccessListener

    interface TransactionSuccessListener {
        fun changeFragment(fragment: Fragment)
    }
}