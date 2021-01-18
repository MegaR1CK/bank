package com.hfad.worldskillsbank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelValute
import kotlinx.android.synthetic.main.item_valute.view.*

class ValuteAdapter(val valutes: List<ModelValute>) : RecyclerView.Adapter<ValuteAdapter.ViewHolder>() {

    inner class ViewHolder(val container: ConstraintLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_valute, parent, false) as ConstraintLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val valute = valutes[position]
        view.valute_code.text = valute.CharCode
        view.valute_name.text = valute.Name
        view.valute_value.text = valute.Value
    }

    override fun getItemCount() = valutes.size
}