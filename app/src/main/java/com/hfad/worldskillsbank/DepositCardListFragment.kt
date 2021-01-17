package com.hfad.worldskillsbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_deposit_card_list.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DepositCardListFragment(val card: ModelCard) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CardInfoFragment(card))
                .commit()
        }


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