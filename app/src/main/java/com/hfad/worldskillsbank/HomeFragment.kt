package com.hfad.worldskillsbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.recycler_cards.layoutManager = LinearLayoutManager(activity)
        view.recycler_checks.layoutManager = LinearLayoutManager(activity)
        //view.recycler_credits.layoutManager = LinearLayoutManager(activity)
        view.recycler_credits.layoutManager = object : LinearLayoutManager(activity) {
            override fun canScrollVertically() = false
        }

        val token = (activity as HomeActivity).token
        App.MAIN_API.getCards(ModelToken(token)).enqueue(object : Callback<List<ModelCard>> {
            override fun onResponse(call: Call<List<ModelCard>>, response: Response<List<ModelCard>>) {
                response.body()?.let { view.recycler_cards.adapter = HomeAdapter(it) }
            }
            override fun onFailure(call: Call<List<ModelCard>>, t: Throwable) {}
        })

        App.MAIN_API.getChecks(ModelToken(token)).enqueue(object : Callback<List<ModelCheck>> {
            override fun onResponse(call: Call<List<ModelCheck>>, response: Response<List<ModelCheck>>) {
                response.body()?.let { view.recycler_checks.adapter = HomeAdapter(it) }
            }
            override fun onFailure(call: Call<List<ModelCheck>>, t: Throwable) {}
        })

        App.MAIN_API.getCredits(ModelToken(token)).enqueue(object : Callback<List<ModelCredit>> {
            override fun onResponse(call: Call<List<ModelCredit>>, response: Response<List<ModelCredit>>) {
                response.body()?.let { view.recycler_credits.adapter = HomeAdapter(it) }
            }
            override fun onFailure(call: Call<List<ModelCredit>>, t: Throwable) {}
        })

        return view
    }
}