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
import com.hfad.worldskillsbank.adapters.DepositCardListAdapter
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelToken
import kotlinx.android.synthetic.main.fragment_deposit_card_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DepositCardListFragment(val card: ModelCard) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_deposit_card_list, container,
            false)
        view.recycler_card_list.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getCards(ModelToken((activity as HomeActivity).token))
            .enqueue(object : Callback<List<ModelCard>> {
            override fun onResponse(call: Call<List<ModelCard>>,
                                    response: Response<List<ModelCard>>) {
                val cardList = response.body()?.toMutableList()
                cardList?.remove(card)
                val adapter = cardList?.toList()?.let { DepositCardListAdapter(it) }
                adapter?.depositListener = object : DepositCardListAdapter.DepositListener {
                    override fun changeFragment(cardSource: ModelCard) {
                        parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_container, DepositSumFragment(cardSource, card))
                                .addToBackStack("CARD")
                                .commit()
                    }
                }
                view.recycler_card_list.adapter = adapter
            }
            override fun onFailure(call: Call<List<ModelCard>>, t: Throwable) {}
        })
        return view
    }
}