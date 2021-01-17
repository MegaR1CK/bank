package com.hfad.worldskillsbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_transaction_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardHistoryFragment(private val card: ModelCard) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            val ft = parentFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, CardInfoFragment(card))
            ft.commit()
        }

        val view = inflater.inflate(R.layout.fragment_transaction_history, container,
            false)

        view.recycler_transaction_history.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getCardTransactions(ModelCardPost((activity as HomeActivity).token,
            card.cardNumber)).enqueue(object : Callback<List<ModelTransaction>> {
            override fun onResponse(
                call: Call<List<ModelTransaction>>,
                response: Response<List<ModelTransaction>>) {
                view.recycler_transaction_history.adapter = response.body()?.let { it1 ->
                    TransactionHistoryAdapter(it1.toMutableList()
                            .sortedBy { it.date }.reversed(), card)
                }
            }
            override fun onFailure(call: Call<List<ModelTransaction>>, t: Throwable) {}
        })
        return view
    }
}