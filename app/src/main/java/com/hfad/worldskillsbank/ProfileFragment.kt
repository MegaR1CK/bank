package com.hfad.worldskillsbank

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment(val prevFragment: Fragment) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false) as ListView

        (activity as HomeActivity).toolbar.setNavigationOnClickListener {
            val ft = parentFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, prevFragment)
            ft.commit()
            (activity as HomeActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

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
                    val ft = parentFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, LastLoginFragment(prevFragment))
                    ft.commit()
                }

                3 -> {
                    val ft = parentFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, InformationFragment(prevFragment))
                    ft.commit()
                }

                4 -> {
                    App.MAIN_API.logout(ModelToken((activity as HomeActivity).token)).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            val preferences = PreferenceManager
                                .getDefaultSharedPreferences(activity)
                            val editor = preferences.edit()
                            editor.clear()
                            editor.apply()
                            if (App.WAS_AUTHORIZED)
                                startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                        }
                        override fun onFailure(call: Call<Void>, t: Throwable) {}
                    })
                }
            }
        }
        return view
    }
}