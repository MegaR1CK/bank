package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.dialogs.EditLoginDialog
import com.hfad.worldskillsbank.models.ModelToken

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false) as ListView

        view.setOnItemClickListener { parent, _, position, id ->
            when (position) {
                0 -> {
                    val dialog = EditLoginDialog(EditLoginDialog.EditType.EditPassword)
                    val ft = parentFragmentManager.beginTransaction()
                    dialog.show(ft, "editLogin")
                }

                1 -> {
                    val dialog = EditLoginDialog(EditLoginDialog.EditType.EditUsername)
                    val ft = parentFragmentManager.beginTransaction()
                    dialog.show(ft, "editUsername")
                }

                2 -> {
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, LastLoginFragment())
                            .addToBackStack("PROFILE")
                            .commit()
                }

                3 -> {
                    parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, InformationFragment())
                            .addToBackStack("PROFILE")
                            .commit()
                }

                4 -> {
                    App.logout(ModelToken(App.TOKEN),
                            activity as HomeActivity)
                }
            }
        }
        return view
    }
}