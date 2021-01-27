package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.adapters.CardListAdapter
import com.hfad.worldskillsbank.models.ModelCard
import kotlinx.android.synthetic.main.fragment_transfer_list.*
import kotlinx.android.synthetic.main.fragment_transfer_list.view.*

class TransferListFragment (val sourceCard: ModelCard) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transfer_list, container, false)

        val cardList = App.USER?.cards?.toMutableList()
        cardList?.remove(sourceCard)
        val adapter = CardListAdapter(cardList?.toList() ?: mutableListOf())
        adapter.cardListListener = object : CardListAdapter.CardListListener<ModelCard> {
            override fun changeFragment(card: ModelCard) {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                        TransactionSumFragment(sourceCard, card))
                    .addToBackStack("TRANSACTION")
                    .commit()
            }
        }
        view.recycler_transfer_list.layoutManager = LinearLayoutManager(activity)
        view.recycler_transfer_list.adapter = adapter

        view.custom_card_next_btn.setOnClickListener {
            if (custom_card_field.text.length != 16)
                Toast.makeText(activity, R.string.card_num_incorrect, Toast.LENGTH_SHORT).show()
            else {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                        TransactionSumFragment(sourceCard, ModelCard(custom_card_field.text.toString(),
                            getString(R.string.card_dest_title), -1.0, "", false)))
                    .addToBackStack("TRANSACTION")
                    .commit()
            }
        }

        return view
    }
}