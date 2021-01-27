package com.hfad.worldskillsbank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelTemplate
import com.hfad.worldskillsbank.models.ModelTemplatePost
import kotlinx.android.synthetic.main.item_template.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TemplatesAdapter(private val elements: MutableList<ModelTemplate>) :
    RecyclerView.Adapter<TemplatesAdapter.ViewHolder>() {

    inner class ViewHolder(val container: SwipeRevealLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cl = LayoutInflater.from(parent.context).inflate(R.layout.item_template,
            parent, false) as SwipeRevealLayout
        return ViewHolder(cl)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val element = elements[position]
        view.template_name.text = element.name

        view.template_delete.setOnClickListener {

            App.MAIN_API.deleteTemplate(ModelTemplatePost(
                element.id, element.name, element.destNumber,
                element.cardNumber, element.sum, element.owner, App.USER?.token ?: ""
            )).enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    val pos = holder.adapterPosition
                    elements.removeAt(pos)
                    notifyItemRemoved(pos)
                    notifyItemRangeChanged(pos, elements.size)
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {}
            })
        }

        view.template_edit.setOnClickListener {
            templateListener.editTemplate(element)
        }

        view.template_layout.setOnClickListener {
            templateListener.changeFragment(element.destNumber, element.cardNumber, element.sum)
        }
    }

    override fun getItemCount() = elements.size

    lateinit var templateListener: TemplateListener

    interface TemplateListener {
        fun changeFragment(destNumber: String, cardNumber: String, sum: Double)
        fun editTemplate(template: ModelTemplate)
    }
}