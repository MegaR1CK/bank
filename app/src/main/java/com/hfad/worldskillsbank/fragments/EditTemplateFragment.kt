package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.models.ModelTemplate
import com.hfad.worldskillsbank.models.ModelTemplatePost
import kotlinx.android.synthetic.main.fragment_payment_accept.*
import kotlinx.android.synthetic.main.fragment_payment_accept.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTemplateFragment(private val template: ModelTemplate) : Fragment() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_payment_accept, container, false)

        view.payment_btn.text = getString(R.string.template_edit)
        view.payment_num_field.setText(template.destNumber, TextView.BufferType.EDITABLE)
        view.payment_sum_field.setText(template.sum.toString(), TextView.BufferType.EDITABLE)

        val cardNumbers = mutableListOf<String>()
        App.USER?.cards?.forEach { cardNumbers.add(it.cardNumber) }
        val adapter = activity?.let { ArrayAdapter(it,
            android.R.layout.simple_spinner_dropdown_item, cardNumbers) }
        view.payment_spinner.adapter = adapter
        adapter?.getPosition(template.cardNumber)?.let {
            view.payment_spinner.setSelection(it)
        }

        view.payment_btn.setOnClickListener {
            val sum = payment_sum_field.text.toString().toDoubleOrNull()
            if (sum != null)
                App.MAIN_API.editTemplate(ModelTemplatePost(
                    template.id,
                    template.name,
                    view.payment_num_field.text.toString(),
                    view.payment_spinner.selectedItem.toString(),
                    sum, template.owner, App.USER?.token ?: "")
                ).enqueue(object : Callback<Void> {

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_container, TemplatesFragment())
                                .commit()
                        }
                        else activity?.let { App.errorAlert(response.message(), it) }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        t.message?.let { it1 -> activity?.let { it2 -> App.errorAlert(it1, it2) } }
                    }
                })
            else Toast.makeText(activity, getString(R.string.transaction_wrong_sum),
                Toast.LENGTH_SHORT).show()
        }
        return view
    }
}