package com.hfad.worldskillsbank.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelBankomat
import kotlinx.android.synthetic.main.item_bankomat.view.*
import java.text.SimpleDateFormat
import java.util.*

class BankomatsAdapter(private val bankomats: List<ModelBankomat>) : RecyclerView.Adapter<BankomatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_bankomat, parent, false) as ConstraintLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val bankomat = bankomats[position]
        view.text_address.text = bankomat.address
        view.text_type.text = bankomat.type
        view.text_time.text = String.format(Locale.getDefault(),
            holder.container.context.getString(R.string.bankomat_time), bankomat.timeOpen, bankomat.timeClose)

        val formatter = SimpleDateFormat("hh:mm", Locale.getDefault())
        val timeOpen = formatter.parse(bankomat.timeOpen)
        val timeClose = formatter.parse(bankomat.timeClose)
        val currentTime = formatter.parse(formatter.format(Date()))
        if (currentTime != null && currentTime.after(timeOpen) && currentTime.before(timeClose)) {
            view.text_status.text = "Работает"
            view.text_status.setTextColor(Color.parseColor("#60f3c2"))
        }
        else {
            view.text_status.text = "Не работает"
            view.text_status.setTextColor(Color.RED)
        }

        view.setOnClickListener {
            bankomatListener.moveCard(position)
        }
    }

    override fun getItemCount() = bankomats.size

    inner class ViewHolder(val container: ConstraintLayout) : RecyclerView.ViewHolder(container)

    lateinit var bankomatListener: BankomatListener

    interface BankomatListener {
        fun moveCard(pos: Int)
    }
}