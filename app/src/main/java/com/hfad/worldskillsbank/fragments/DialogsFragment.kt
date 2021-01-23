package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*

class DialogsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as HomeActivity).onCreateOptionsMenu((activity as HomeActivity).toolbar.menu)
        return inflater.inflate(R.layout.fragment_dialogs, container, false)
    }
}