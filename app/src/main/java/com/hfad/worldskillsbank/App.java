package com.hfad.worldskillsbank;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/MegaR1CK/bank/master/JSONs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Api api = retrofit.create(Api.class);
}
