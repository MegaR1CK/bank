package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.adapters.TransactionHistoryAdapter
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelCheckPost
import com.hfad.worldskillsbank.models.ModelTransaction
import kotlinx.android.synthetic.main.fragment_transaction_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckHistoryFragment(private val check: ModelCheck) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_transaction_history, container,
            false)

        view.recycler_transaction_history.layoutManager = LinearLayoutManager(activity)

        view.history_pb.visibility = View.VISIBLE
        App.MAIN_API.getCheckTransactions(ModelCheckPost(App.USER?.token ?: "",
            check.checkNumber)).enqueue(object : Callback<List<ModelTransaction>> {
            override fun onResponse(call: Call<List<ModelTransaction>>,
                                    response: Response<List<ModelTransaction>>) {
                if (response.isSuccessful) {
                    view.history_pb.visibility = View.INVISIBLE
                    view.recycler_transaction_history.adapter = response.body()?.let { it1 ->
                        TransactionHistoryAdapter(it1.toMutableList()
                            .sortedBy { it.date }.reversed(), check)
                    }
                }
                else activity?.let { App.errorAlert(response.message(), it) }
            }
            override fun onFailure(call: Call<List<ModelTransaction>>, t: Throwable) {
                t.message?.let { activity?.let { it1 -> App.errorAlert(it, it1) } }
                view.history_pb.visibility = View.INVISIBLE
            }
        })
        return view
    }
}