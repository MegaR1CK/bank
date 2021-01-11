package com.hfad.worldskillsbank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_home.view.*
import java.lang.StringBuilder
import java.util.*

class HomeAdapter<T>(val elements: List<T>) : RecyclerView.Adapter<HomeAdapter<T>.ViewHolder>() {

    inner class ViewHolder(val container: ConstraintLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false) as ConstraintLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        when (val element = elements[position]) {
            is ModelCard -> {
                view.text_name.text = element.cardType

                val cardNumber = element.cardNumber
                val formattedCN = StringBuilder(cardNumber.substring(0..3))
                repeat(element.cardNumber.length - 8) { formattedCN.append("*") }
                formattedCN.append(cardNumber.substring(cardNumber.length - 4, cardNumber.length))
                view.text_desc.text = formattedCN.toString()

                view.text_sum.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
            }
            is ModelCheck -> {
                view.text_name.text = element.checkName

                val checkNumber = element.checkNumber
                val formattedCN = StringBuilder()
                repeat(checkNumber.length - 6) { formattedCN.append("*") }
                formattedCN.append(checkNumber.substring(checkNumber.length - 6, checkNumber.length))
                view.text_desc.text = formattedCN.toString()

                view.text_sum.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
            }
            is ModelCredit -> {
                view.text_name.text = element.creditName
                view.text_desc.text = element.creditDate
                view.text_sum.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
            }
        }
    }

    override fun getItemCount() = elements.size
}