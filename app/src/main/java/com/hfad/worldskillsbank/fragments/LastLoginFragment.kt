package com.hfad.worldskillsbank.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.activities.HomeActivity
import com.hfad.worldskillsbank.adapters.LastLoginAdapter
import com.hfad.worldskillsbank.models.ModelLastLogin
import com.hfad.worldskillsbank.models.ModelToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LastLoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_last_login,
            container, false) as RecyclerView

        view.layoutManager = LinearLayoutManager(activity)

        App.MAIN_API.getLastLogin(ModelToken(App.TOKEN))
            .enqueue(object : Callback<List<ModelLastLogin>> {
                override fun onResponse(call: Call<List<ModelLastLogin>>,
                                        response: Response<List<ModelLastLogin>>) {
                    view.adapter = response.body()?.let { LastLoginAdapter(it) }
                }

                override fun onFailure(call: Call<List<ModelLastLogin>>, t: Throwable) {}
            })
        return view
    }
}