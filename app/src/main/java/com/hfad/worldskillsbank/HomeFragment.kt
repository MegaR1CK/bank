package com.hfad.worldskillsbank

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.recycler_cards.layoutManager = LinearLayoutManager(activity)
        view.recycler_checks.layoutManager = LinearLayoutManager(activity)
        view.recycler_credits.layoutManager = LinearLayoutManager(activity)

        val token = (activity as HomeActivity).token

        activity?.title = getString(R.string.loading_title)
        for (v in view.recycler_layout) v.visibility = View.GONE

        App.MAIN_API.getUser(ModelToken(token)).enqueue(object : Callback<ModelUser> {
            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                for (v in view.recycler_layout) v.visibility = View.VISIBLE
                response.body()?.let { (activity as HomeActivity).title = it.name + " " + it.midname }
            }
            override fun onFailure(call: Call<ModelUser>, t: Throwable) {}
        })

        App.MAIN_API.getCards(ModelToken(token)).enqueue(object : Callback<List<ModelCard>> {
            override fun onResponse(call: Call<List<ModelCard>>,
                                    response: Response<List<ModelCard>>) {
                response.body()?.let {
                    val adapter = HomeAdapter(it)
                    adapter.fragmentReplaceListener = object : HomeAdapter.FragmentReplaceListener {
                        override fun replaceFragment(fragment: Fragment) {
                            (activity as HomeActivity)
                                    .supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit()
                        }
                    }
                    view.recycler_cards.adapter = adapter
                }
            }
            override fun onFailure(call: Call<List<ModelCard>>, t: Throwable) {}
        })

        App.MAIN_API.getChecks(ModelToken(token)).enqueue(object : Callback<List<ModelCheck>> {
            override fun onResponse(call: Call<List<ModelCheck>>,
                                    response: Response<List<ModelCheck>>) {
                response.body()?.let {
                    val adapter = HomeAdapter(it)
                    adapter.fragmentReplaceListener = object : HomeAdapter.FragmentReplaceListener {
                        override fun replaceFragment(fragment: Fragment) {
                            (activity as HomeActivity)
                                .supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit()
                        }
                    }
                    view.recycler_checks.adapter = adapter
                }
            }
            override fun onFailure(call: Call<List<ModelCheck>>, t: Throwable) {}
        })

        App.MAIN_API.getCredits(ModelToken(token)).enqueue(object : Callback<List<ModelCredit>> {
            override fun onResponse(call: Call<List<ModelCredit>>,
                                    response: Response<List<ModelCredit>>) {
                response.body()?.let { view.recycler_credits.adapter = HomeAdapter(it) }
            }
            override fun onFailure(call: Call<List<ModelCredit>>, t: Throwable) {}
        })

        return view
    }
}