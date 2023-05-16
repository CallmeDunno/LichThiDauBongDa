package com.example.lichthidaubongda.fragment.home.api;

import com.example.lichthidaubongda.fragment.home.model.DataLeague;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiLeague {
    String url = "https://gw.vnexpress.net/football/";

    Gson gson = new GsonBuilder().create();

    ApiLeague apiLeague = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiLeague.class);

    @GET("standing?league_id=4335")
    Call<DataLeague> getLeague();
}
