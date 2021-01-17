package com.hfad.worldskillsbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_deposit_sum.*
import kotlinx.android.synthetic.main.fragment_deposit_sum.view.*

class DepositSumFragment(private val cardSource: ModelCard, private val  cardDest: ModelCard) : Fragment() {

    lateinit var prevCardDestName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_deposit_sum, container,
            false)

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            cardDest.cardType = prevCardDestName
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, DepositCardListFragment(cardDest))
                .commit()
        }

        view.recycler_cards_for_dep.layoutManager = LinearLayoutManager(activity)
        cardSource.cardType = getString(R.string.card_source_title)
        prevCardDestName = cardDest.cardType
        cardDest.cardType = getString(R.string.card_dest_title)
        val cardList = listOf(cardSource, cardDest)
        view.recycler_cards_for_dep.adapter = HomeAdapter(cardList)

        view.deposit_accept_btn.setOnClickListener {
            val sum = view.field_dep_sum.text.toString().toDoubleOrNull()
            if (sum == null)
                Toast.makeText(activity, getString(R.string.transaction_wrong_sum),
                        Toast.LENGTH_SHORT).show()
            else {
                val dialog = AcceptTransactionDialog(cardSource.cardNumber, cardDest.cardNumber, sum)
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
        }
        return view
    }
}