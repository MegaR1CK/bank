package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.dialogs.CardBlockDialog
import com.hfad.worldskillsbank.dialogs.RenameDialog
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.other.NumberFormatter
import kotlinx.android.synthetic.main.fragment_card_info.view.*
import kotlinx.android.synthetic.main.item_card.view.*

class CardInfoFragment(private val card: ModelCard) : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_card_info, container, false)

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

        view.deposit_btn.setOnClickListener(this)
        view.transfer_btn.setOnClickListener(this)

        view.card_info_list.setOnItemClickListener { parent, _, position, id ->
            when (position) {
                0 -> {
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, CardHistoryFragment(card))
                            .addToBackStack("CARD")
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.deposit_btn -> {
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, DepositListFragment(card))
                        .addToBackStack("TRANSACTION")
                        .commit()
            }
            R.id.transfer_btn -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, TransferListFragment(card))
                    .addToBackStack("TRANSACTION")
                    .commit()
            }
        }
    }
}