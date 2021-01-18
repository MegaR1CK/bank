package com.hfad.worldskillsbank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelLastLogin
import kotlinx.android.synthetic.main.item_last_login.view.*

class LastLoginAdapter(val elements: List<ModelLastLogin>) : RecyclerView.Adapter<LastLoginAdapter.ViewHolder>() {

    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_last_login, parent, false) as LinearLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = elements[position]
        holder.container.last_login_date.text = element.date
        holder.container.last_login_time.text = element.time
    }

    override fun getItemCount() = elements.size
}