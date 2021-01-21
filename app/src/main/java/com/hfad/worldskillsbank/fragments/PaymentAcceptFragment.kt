package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.dialogs.AcceptTransactionDialog
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.other.NumberFormatter
import kotlinx.android.synthetic.main.fragment_payment_accept.*
import kotlinx.android.synthetic.main.fragment_payment_accept.view.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentAcceptFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_payment_accept, container, false)

        App.MAIN_API.getCards(ModelToken(App.TOKEN)).enqueue(object: Callback<List<ModelCard>> {
            override fun onResponse(call: Call<List<ModelCard>>,
                                    response: Response<List<ModelCard>>) {
                val cardNumbers = mutableListOf<String>()
                response.body()?.forEach { cardNumbers.add(it.cardNumber) }
                val adapter = activity?.let { ArrayAdapter(it,
                    android.R.layout.simple_spinner_dropdown_item, cardNumbers) }
                view.payment_spinner.adapter = adapter
            }
            override fun onFailure(call: Call<List<ModelCard>>, t: Throwable) {}
        })

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