package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.adapters.HomeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    @ObsoleteCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.recycler_cards.layoutManager = LinearLayoutManager(activity)
        view.recycler_checks.layoutManager = LinearLayoutManager(activity)
        view.recycler_credits.layoutManager = LinearLayoutManager(activity)

        activity?.title = getString(R.string.loading_title)
        for (v in view.recycler_layout) v.visibility = View.GONE

        val menuItem = (activity as HomeActivity).bottom_nav_view.menu.findItem(R.id.bottom_nav_home)
        if (!menuItem.isChecked) menuItem.isChecked = true

        if (App.CARDS.isEmpty())
            runBlocking(newSingleThreadContext("CARDS")) {
                App.updateCardList()
            }
        if (App.CHECKS.isEmpty())
            runBlocking(newSingleThreadContext("CHECKS")) {
                App.updateCheckList()
            }
        if (App.CREDITS.isEmpty())
            runBlocking(newSingleThreadContext("CREDITS")) {
                App.updateCreditsList()
            }
        if (App.USERNAME == null)
            runBlocking(newSingleThreadContext("USER")) {
                App.updateUsername()
            }

        for (v in view.recycler_layout) v.visibility = View.VISIBLE
        (activity as HomeActivity).title = App.USERNAME
        view.recycler_cards.adapter = getHomeAdapter(App.CARDS)
        view.recycler_checks.adapter = getHomeAdapter(App.CHECKS)
        view.recycler_credits.adapter = getHomeAdapter(App.CREDITS)

        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as HomeActivity).onCreateOptionsMenu((activity as HomeActivity).toolbar.menu)

        return view
    }

    private fun <T> getHomeAdapter(list: List<T>): HomeAdapter<T>{
        val adapter = HomeAdapter(list)
        adapter.fragmentReplaceListener = object : HomeAdapter.FragmentReplaceListener {
            override fun replaceFragment(fragment: Fragment) {
                (activity as HomeActivity)
                    .supportActionBar?.setDisplayHomeAsUpEnabled(true)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack("INFO")
                    .commit()
            }
        }
        return adapter
    }
}