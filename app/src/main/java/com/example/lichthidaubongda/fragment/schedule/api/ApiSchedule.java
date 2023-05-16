package com.example.lichthidaubongda.fragment.schedule.api;

import com.example.lichthidaubongda.fragment.schedule.model.DataSchedules;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiSchedule {

    String url = "https://gw.vnexpress.net/football/";

    Gson gson = new GsonBuilder().create();

    ApiSchedule apiSchedule = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiSchedule.class);

    @GET("fixture?league_id=4335")
    Call<DataSchedules> getListSchedule();
}
