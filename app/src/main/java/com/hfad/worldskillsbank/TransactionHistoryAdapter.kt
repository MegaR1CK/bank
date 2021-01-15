package com.hfad.worldskillsbank

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_transaction_history.view.*
import java.util.*

class TransactionHistoryAdapter(private val elements: List<ModelTransaction>) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_history,
            parent, false) as LinearLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val element = elements[position]
        view.history_elem_title.text =
            if (element.number.length == 16)
                view.context.getString(R.string.history_card_title)
            else view.context.getString(R.string.history_check_title)
        view.history_elem_date.text = element.date
        view.history_elem_sum.text = String.format(Locale.getDefault(),
            view.context.getString(R.string.home_sum), element.sum)
    }

    override fun getItemCount() = elements.size
}