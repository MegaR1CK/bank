package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.worldskillsbank.other.NumberFormatter
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.dialogs.RenameDialog
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.models.ModelCheck
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_check_info.view.*
import java.util.*

class CheckInfoFragment(private val check: ModelCheck) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_check_info, container, false)

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        view.check_info_name.text = check.checkName
        view.check_info_balance.text = String.format(Locale.getDefault(),
                getString(R.string.home_sum), check.balance)
        view.check_info_number.text = NumberFormatter.formatNumber(check.checkNumber,
                0, 6)


        view.check_info_list.setOnItemClickListener { parent, _, position, id ->
            when (position) {
                0 -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, CheckHistoryFragment(check))
                        .commit()
                }
                1 -> {
                    val dialog = RenameDialog(check)
                    dialog.show(parentFragmentManager.beginTransaction(), "RENAME")
                }
            }
        }
        return view
    }
}