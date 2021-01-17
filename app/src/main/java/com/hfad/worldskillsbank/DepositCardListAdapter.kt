package com.hfad.worldskillsbank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import java.util.*

class DepositCardListAdapter(private val elements: List<ModelCard>) : RecyclerView.Adapter<DepositCardListAdapter.ViewHolder>() {
    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false) as LinearLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val element = elements[position]
        view.card_info_name.text = element.cardType
        view.card_info_number.text = NumberFormatter.formatNumber(element.cardNumber,
            4, 4)
        view.card_info_balance.text = String.format(Locale.getDefault(),
            view.context.getString(R.string.home_sum), element.balance)
        if (element.isBlocked) view.card_info_block.visibility = View.VISIBLE
        else {
            view.setOnClickListener {
                depositListener.changeFragment(element)
            }
        }
    }

    override fun getItemCount() = elements.size

    lateinit var depositListener: DepositListener

    interface DepositListener {
        fun changeFragment(cardSource: ModelCard)
    }
}