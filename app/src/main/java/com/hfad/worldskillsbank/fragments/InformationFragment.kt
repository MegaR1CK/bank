package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_information.view.*

class InformationFragment(private val prevFragment: Fragment) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            val ft = parentFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, ProfileFragment(prevFragment))
            ft.commit()
        }

        val view = inflater.inflate(R.layout.fragment_information, container, false)
        view.info_link.movementMethod = LinkMovementMethod.getInstance()
        return view
    }

}