package com.hfad.worldskillsbank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelTransaction
import kotlinx.android.synthetic.main.item_transaction_history.view.*
import java.util.*

class TransactionHistoryAdapter<T>(private val elements: List<ModelTransaction>, val item: T) :
    RecyclerView.Adapter<TransactionHistoryAdapter<T>.ViewHolder>() {

    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_history,
            parent, false) as LinearLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val context = view.context
        val element = elements[position]

        view.history_elem_title.text =
            if (item is ModelCard && element.destNumber == item.cardNumber)
                 context.getString(R.string.history_card_title_to)
            else if (item is ModelCard && element.sourceNumber == item.cardNumber)
                 context.getString(R.string.history_card_title_from)
            else if (item is ModelCheck && element.destNumber == item.checkNumber)
                 context.getString(R.string.history_check_title_to)
            else context.getString(R.string.history_check_title_from)

        view.history_elem_date.text = element.date

        if (view.history_elem_title.text == context.getString(R.string.history_check_title_to) ||
                view.history_elem_title.text == context.getString(R.string.history_card_title_to))
            view.history_elem_sum.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum_to), element.sum)
        else view.history_elem_sum.text = String.format(Locale.getDefault(),
                view.context.getString(R.string.home_sum_from), element.sum)
    }

    override fun getItemCount() = elements.size
}