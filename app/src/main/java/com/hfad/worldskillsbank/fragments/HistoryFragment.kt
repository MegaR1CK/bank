package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.adapters.GeneralHistoryAdapter
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelToken
import com.hfad.worldskillsbank.models.ModelTransaction
import kotlinx.android.synthetic.main.activity_home.*
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

        var transactions: List<ModelTransaction>? = listOf()

        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as HomeActivity).onCreateOptionsMenu((activity as HomeActivity).toolbar.menu)

        view.recycler_transaction_history.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getTransactions(ModelToken(App.USER?.token ?: ""))
                .enqueue(object : Callback<List<ModelTransaction>> {
                    override fun onResponse(call: Call<List<ModelTransaction>>,
                                            response: Response<List<ModelTransaction>>) {
                        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                        transactions = response.body()?.sortedBy { sdf.parse(it.date) }?.reversed()
                        view.recycler_transaction_history.adapter = transactions?.let {
                            GeneralHistoryAdapter(it)
                        }
                    }
                    override fun onFailure(call: Call<List<ModelTransaction>>, t: Throwable) {}
                })

        ((activity as HomeActivity).toolbar.menu.findItem(R.id.menu_search).actionView as SearchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    view.recycler_transaction_history.adapter =
                        transactions?.filter {
                            it.date.contains(newText ?: "") ||
                                    it.destNumber.substring(it.destNumber.length - 4,
                                        it.destNumber.length).contains(newText ?: "") ||
                                    it.sourceNumber.substring(it.sourceNumber.length - 4,
                                        it.sourceNumber.length).contains(newText ?: "") ||
                                    it.sum.toString().contains(newText ?: "")
                        }?.let { GeneralHistoryAdapter(it) }
                    return false
                }
            })

        return view
    }
}