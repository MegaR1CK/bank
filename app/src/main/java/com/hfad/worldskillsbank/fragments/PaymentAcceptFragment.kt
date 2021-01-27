package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.dialogs.AcceptTransactionDialog
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelToken
import kotlinx.android.synthetic.main.fragment_payment_accept.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentAcceptFragment(private val paymentNum: String = "",
                            val cardNumber: String = "",
                            private val paymentSum: Double = 0.0) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_payment_accept, container, false)

        if (paymentNum != "" && paymentSum != 0.0) {
            view.payment_num_field.setText(paymentNum, TextView.BufferType.EDITABLE)
            view.payment_sum_field.setText(paymentSum.toString(), TextView.BufferType.EDITABLE)
        }

        val cardNumbers = mutableListOf<String>()
        App.CARDS.forEach { cardNumbers.add(it.cardNumber) }
        val adapter = activity?.let { ArrayAdapter(it,
            android.R.layout.simple_spinner_dropdown_item, cardNumbers) }
        view.payment_spinner.adapter = adapter
        if (cardNumber != "")
            adapter?.getPosition(cardNumber)?.let { view.payment_spinner.setSelection(it) }

        view.payment_btn.setOnClickListener {
            val sum = view.payment_sum_field.text.toString().toDoubleOrNull()
            if (sum != null) {
                val dialog = AcceptTransactionDialog(view.payment_spinner.selectedItem.toString(),
                    view.payment_num_field.text.toString(), sum)
                dialog.transactionSuccessListener = object :
                    AcceptTransactionDialog.TransactionSuccessListener {
                    override fun changeFragment(fragment: Fragment) {
                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit()
                    }
                }
                dialog.show(parentFragmentManager.beginTransaction(), "TRANSACTION")
            }
            else Toast.makeText(activity, R.string.transaction_wrong_sum, Toast.LENGTH_SHORT).show()
        }
        return view
    }
}