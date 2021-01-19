package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.R
import kotlinx.android.synthetic.main.fragment_information.view.*

class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_information, container, false)
        view.info_link.movementMethod = LinkMovementMethod.getInstance()
        return view
    }

}