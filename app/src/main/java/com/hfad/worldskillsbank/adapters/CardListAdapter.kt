package com.hfad.worldskillsbank.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.other.NumberFormatter
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import kotlinx.android.synthetic.main.item_card.view.*
import java.util.*

class CardListAdapter<T>(private val elements: List<T>) :
        RecyclerView.Adapter<CardListAdapter<T>.ViewHolder>() {

    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false) as LinearLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val element = elements[position]

        if (element is ModelCard) {
            view.card_info_name.text = element.cardType
            view.card_info_number.text = NumberFormatter.formatNumber(element.cardNumber,
                    4, 4)
            view.card_info_balance.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
            if (element.isBlocked) view.card_info_block.visibility = View.VISIBLE
            else view.setOnClickListener { cardListListener.changeFragment(element) }
        }

        if (element is ModelCheck) {
            view.card_info_name.text = element.checkName
            view.card_info_number.text = NumberFormatter.formatNumber(element.checkNumber,
                    0, 6)
            view.card_info_balance.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
            view.setOnClickListener { cardListListener.changeFragment(element) }
        }

    }

    override fun getItemCount() = elements.size

    lateinit var cardListListener: CardListListener<T>

    interface CardListListener<T> {
        fun changeFragment(card: T)
    }
}