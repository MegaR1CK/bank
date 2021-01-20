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
import com.hfad.worldskillsbank.adapters.CardListAdapter
import com.hfad.worldskillsbank.models.ModelCard
import com.hfad.worldskillsbank.models.ModelCheck
import com.hfad.worldskillsbank.models.ModelToken
import kotlinx.android.synthetic.main.fragment_deposit_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DepositListFragment(val destCard: ModelCard) : Fragment() {

    var cardList: MutableList<ModelCard>? = null
    var checkList: MutableList<ModelCheck>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_deposit_list, container,
                false)

        val cardDepListener = object : CardListAdapter.CardListListener<ModelCard> {
            override fun changeFragment(card: ModelCard) {
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, TransactionSumFragment(card, destCard))
                        .addToBackStack("TRANSACTION")
                        .commit()
            }
        }
        val checkDepListener = object : CardListAdapter.CardListListener<ModelCheck> {
            override fun changeFragment(card: ModelCheck) {
                parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, TransactionSumFragment(card, destCard))
                        .addToBackStack("TRANSACTION")
                        .commit()
            }
        }

        view.recycler_deposit_list.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getCards(ModelToken((activity as HomeActivity).token))
                .enqueue(object : Callback<List<ModelCard>> {
                    override fun onResponse(call: Call<List<ModelCard>>,
                                            response: Response<List<ModelCard>>) {
                        cardList = response.body()?.toMutableList()
                        cardList?.remove(destCard)
                        val adapter = cardList?.toList()?.let { CardListAdapter(it) }
                        adapter?.cardListListener = cardDepListener
                        view.recycler_deposit_list.adapter = adapter
                    }

                    override fun onFailure(call: Call<List<ModelCard>>, t: Throwable) {}
                })

        App.MAIN_API.getChecks(ModelToken((activity as HomeActivity).token))
                .enqueue(object : Callback<List<ModelCheck>> {
                    override fun onResponse(call: Call<List<ModelCheck>>,
                                            response: Response<List<ModelCheck>>) {
                        checkList = response.body()?.toMutableList()
                    }

                    override fun onFailure(call: Call<List<ModelCheck>>, t: Throwable) {}
                })

        view.payment_type_radio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.payment_type_card) {
                val replaceAdapter = cardList?.toList()?.let { CardListAdapter(it) }
                replaceAdapter?.cardListListener = cardDepListener
                view.recycler_deposit_list.adapter = replaceAdapter
            }
            else {
                val replaceAdapter = checkList?.toList()?.let { CardListAdapter(it) }
                replaceAdapter?.cardListListener = checkDepListener
                view.recycler_deposit_list.adapter = replaceAdapter
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        view?.payment_type_radio?.check(R.id.payment_type_card)
    }
}