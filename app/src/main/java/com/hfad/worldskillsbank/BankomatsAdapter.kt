package com.hfad.worldskillsbank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bankomat_item.view.*

class BankomatsAdapter(private val bankomats: List<Bankomat>) : RecyclerView.Adapter<BankomatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.bankomat_item, parent, false) as ConstraintLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val bankomat = bankomats[position]
        view.text_address.text = bankomat.address
        view.text_type.text = bankomat.type
    }

    override fun getItemCount() = bankomats.size

    inner class ViewHolder(val container: ConstraintLayout) : RecyclerView.ViewHolder(container)
}