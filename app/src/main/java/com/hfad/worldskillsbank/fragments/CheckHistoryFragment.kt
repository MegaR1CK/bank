package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.adapters.TransactionHistoryAdapter
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelCheckPost
import com.hfad.worldskillsbank.models.ModelTransaction
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_transaction_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckHistoryFragment(private val check: ModelCheck) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            val ft = parentFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, CheckInfoFragment(check))
            ft.commit()
        }

        val view = inflater.inflate(R.layout.fragment_transaction_history, container,
            false)

        view.recycler_transaction_history.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getCheckTransactions(ModelCheckPost((activity as HomeActivity).token,
            check.checkNumber)).enqueue(object : Callback<List<ModelTransaction>> {
            override fun onResponse(
                    call: Call<List<ModelTransaction>>,
                    response: Response<List<ModelTransaction>>) {
                view.recycler_transaction_history.adapter = response.body()?.let { it1 ->
                    TransactionHistoryAdapter(it1.toMutableList()
                            .sortedBy { it.date }.reversed(), check)
                }
            }
            override fun onFailure(call: Call<List<ModelTransaction>>, t: Throwable) {}
        })
        return view
    }
}