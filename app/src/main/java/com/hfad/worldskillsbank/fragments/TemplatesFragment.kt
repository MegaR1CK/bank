package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.adapters.TemplatesAdapter
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.models.ModelTemplate
import kotlinx.android.synthetic.main.fragment_templates.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TemplatesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_templates, container, false)

        view.recycler_templates.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getTemplates(ModelToken(App.USER?.token ?: ""))
            .enqueue(object : Callback<List<ModelTemplate>> {

                override fun onResponse(call: Call<List<ModelTemplate>>,
                                        response: Response<List<ModelTemplate>>) {
                    if (response.isSuccessful) {
                        val adapter = response.body()?.toMutableList()?.let { TemplatesAdapter(it) }
                        adapter?.templateListener = object : TemplatesAdapter.TemplateListener {
                            override fun changeFragment(destNumber: String, cardNumber: String,
                                                        sum: Double) {
                                parentFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.fragment_container,
                                        PaymentAcceptFragment(destNumber, cardNumber, sum))
                                    .addToBackStack("TEMPLATE")
                                    .commit()
                            }
                            override fun editTemplate(template: ModelTemplate) {
                                parentFragmentManager
                                    .beginTransaction()
                                    .replace(
                                        R.id.fragment_container,
                                        EditTemplateFragment(template))
                                    .addToBackStack("TEMPLATE")
                                    .commit()
                            }
                        }
                        view.recycler_templates.adapter = adapter
                    }
                    else activity?.let { App.errorAlert(response.message(), it) }
                }
                override fun onFailure(call: Call<List<ModelTemplate>>, t: Throwable) {
                    t.message?.let { activity?.let { it1 -> App.errorAlert(it, it1) } }
                }
            })

        return view
    }
}