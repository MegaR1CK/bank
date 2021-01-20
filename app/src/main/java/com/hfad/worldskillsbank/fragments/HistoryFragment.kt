package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.adapters.GeneralHistoryAdapter
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.models.ModelTransaction
import kotlinx.android.synthetic.main.fragment_transaction_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_history, container,
                false)

        view.recycler_transaction_history.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getTransactions(ModelToken(App.TOKEN))
                .enqueue(object : Callback<List<ModelTransaction>> {
                    override fun onResponse(call: Call<List<ModelTransaction>>,
                                            response: Response<List<ModelTransaction>>) {
                        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                        view.recycler_transaction_history.adapter =
                                response.body()?.let { it1 -> GeneralHistoryAdapter(it1
                                        .sortedBy { sdf.parse(it.date) }.reversed()) }
                    }
                    override fun onFailure(call: Call<List<ModelTransaction>>, t: Throwable) {}
                })

        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        return view
    }
}