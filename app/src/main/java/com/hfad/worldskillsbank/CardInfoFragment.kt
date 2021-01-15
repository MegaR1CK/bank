package com.hfad.worldskillsbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_card_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class CardInfoFragment(val card: ModelCard) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_card_info, container, false)

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        view.card_info_name.text = card.cardType
        view.card_info_number.text = NumberFormatter.formatNumber(card.cardNumber,
                4, 4)
        view.card_info_balance.text = String.format(getString(R.string.home_sum),
            card.balance)

        if (card.isBlocked) {
            view.card_info_block.visibility = View.VISIBLE
            view.card_info_list.visibility = View.INVISIBLE
            view.deposit_btn.isEnabled = false
            view.transfer_btn.isEnabled = false
        }

        view.card_info_list.setOnItemClickListener { parent, _, position, id ->
            when (position) {
                0 -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, CardHistoryFragment(card))
                        .commit()
                }
                1 -> {
                    val dialog = CardBlockDialog(card)
                    dialog.show(parentFragmentManager.beginTransaction(), "BLOCK")
                }
                2 -> {
                    val dialog = RenameDialog(card)
                    dialog.show(parentFragmentManager.beginTransaction(), "RENAME")
                }
            }
        }

        return view
    }
}