package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.adapters.HomeAdapter
import com.hfad.worldskillsbank.dialogs.AcceptTransactionDialog
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import kotlinx.android.synthetic.main.fragment_transaction_sum.view.*

class TransactionSumFragment<T>(private val source: T, private val cardDest: ModelCard) : Fragment() {

    lateinit var prevCardDestName: String
    lateinit var prevCardSourceName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_sum, container,
            false)

        view.recycler_cards_for_dep.layoutManager = LinearLayoutManager(activity)

        if (source is ModelCard) {
            prevCardSourceName = source.cardType
            source.cardType = getString(R.string.card_source_title)
        }
        if (source is ModelCheck)
            source.checkName = getString(R.string.check_source_title)

        prevCardDestName = cardDest.cardType
        cardDest.cardType = getString(R.string.card_dest_title)

        val cardList = listOf(source, cardDest)
        view.recycler_cards_for_dep.adapter = HomeAdapter(cardList)

        view.deposit_accept_btn.setOnClickListener {
            val sum = view.field_dep_sum.text.toString().toDoubleOrNull()
            if (sum == null)
                Toast.makeText(activity, getString(R.string.transaction_wrong_sum),
                        Toast.LENGTH_SHORT).show()
            else if (source is ModelCard && source.balance < sum ||
                    source is ModelCheck && source.balance < sum)
                Toast.makeText(activity, getString(R.string.not_enough_money),
                    Toast.LENGTH_SHORT).show()
            else {
                var dialog: AcceptTransactionDialog? = null
                if (source is ModelCard)
                    dialog = AcceptTransactionDialog(source.cardNumber, cardDest.cardNumber, sum)
                if (source is ModelCheck)
                    dialog = AcceptTransactionDialog(source.checkNumber, cardDest.cardNumber, sum)

                dialog?.transactionSuccessListener = object :
                        AcceptTransactionDialog.TransactionSuccessListener {
                    override fun changeFragment(fragment: Fragment) {
                        parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit()
                    }
                }
                dialog?.show(parentFragmentManager.beginTransaction(), "TRANSACTION")
            }
        }
        return view
    }

    override fun onStop() {
        super.onStop()
        cardDest.cardType = prevCardDestName
        if (source is ModelCard) source.cardType = prevCardSourceName
    }
}