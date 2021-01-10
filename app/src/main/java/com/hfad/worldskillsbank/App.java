package com.hfad.worldskillsbank;

import android.app.Application;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:57905/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("http://www.cbr.ru/scripts/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public static final ApiXml XML_API = retrofit2.create(ApiXml.class);

    public static final ApiMain MAIN_API = retrofit.create(ApiMain.class);

}
