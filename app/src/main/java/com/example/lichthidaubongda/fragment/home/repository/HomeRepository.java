package com.example.lichthidaubongda.fragment.home.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lichthidaubongda.fragment.home.api.ApiLeague;
import com.example.lichthidaubongda.fragment.home.model.DataLeague;
import com.example.lichthidaubongda.fragment.home.model.League;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private MutableLiveData<League> leagueML;

    public HomeRepository() {
        this.leagueML = new MutableLiveData<>();
    }

    public MutableLiveData<League> getLeagueML(){
        setLeagueML();
        return leagueML;
    }

    private void setLeagueML(){
        ApiLeague.apiLeague.getLeague().enqueue(new Callback<DataLeague>() {
            @Override
            public void onResponse(@NonNull Call<DataLeague> call, @NonNull Response<DataLeague> response) {
                Log.i("Dunno", "League: Success");
                DataLeague dataLeague = response.body();
                if (dataLeague != null){
                    League league = dataLeague.getDataIdLeague().getInforLeague().getLeague();
                    leagueML.postValue(league);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLeague> call, @NonNull Throwable t) {
                Log.e("Dunno", "League: " + t);
            }
        });
    }
}
