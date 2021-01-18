package com.hfad.worldskillsbank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.other.NumberFormatter
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.fragments.CardInfoFragment
import com.hfad.worldskillsbank.fragments.CheckInfoFragment
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelCredit
import kotlinx.android.synthetic.main.item_home.view.*
import java.util.*

class HomeAdapter<T>(private val elements: List<T>) : RecyclerView.Adapter<HomeAdapter<T>.ViewHolder>() {

    inner class ViewHolder(val container: ConstraintLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent,
            false) as ConstraintLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        when (val element = elements[position]) {
            is ModelCard -> {
                view.text_name.text = element.cardType
                view.text_desc.text = NumberFormatter.formatNumber(element.cardNumber,
                        4, 4)
                view.text_sum.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
                if (element.cardType != view.context.getString(R.string.card_source_title) &&
                        element.cardType != view.context.getString(R.string.card_dest_title)) {
                    view.setOnClickListener {
                        fragmentReplaceListener.replaceFragment(CardInfoFragment(element))
                    }
                }
            }
            is ModelCheck -> {
                view.text_name.text = element.checkName
                view.text_desc.text = NumberFormatter.formatNumber(element.checkNumber,
                        0, 6)
                view.text_sum.text = String.format(Locale.getDefault(),
                    view.context.getString(R.string.home_sum), element.balance)
                view.setOnClickListener {
                    fragmentReplaceListener.replaceFragment(CheckInfoFragment(element))
                }
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

    lateinit var fragmentReplaceListener: FragmentReplaceListener

    interface FragmentReplaceListener {
        fun replaceFragment(fragment: Fragment)
    }
}