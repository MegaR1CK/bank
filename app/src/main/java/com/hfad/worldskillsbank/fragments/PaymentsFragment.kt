package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_payments.view.*

class PaymentsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val view = inflater.inflate(R.layout.fragment_payments, container, false)

        view.payment_list.setOnItemClickListener { parent, _, position, id ->
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PaymentAcceptFragment())
                .addToBackStack("PAYMENT")
                .commit()
        }

        return view
    }
}