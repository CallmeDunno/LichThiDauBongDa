package com.example.lichthidaubongda.fragment.chart.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lichthidaubongda.fragment.chart.model.Chart;
import com.example.lichthidaubongda.fragment.home.api.ApiLeague;
import com.example.lichthidaubongda.fragment.home.model.DataLeague;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartRepository {
    private MutableLiveData<List<Chart>> chartML;

    public ChartRepository() {
        chartML = new MutableLiveData<>();
    }

    public MutableLiveData<List<Chart>> getChartML() {
        setChartML();
        return chartML;
    }

    private void setChartML() {
        ApiLeague.apiLeague.getLeague().enqueue(new Callback<DataLeague>() {
            @Override
            public void onResponse(@NonNull Call<DataLeague> call, @NonNull Response<DataLeague> response) {
                Log.i("Dunno", "Chart: Success");
                DataLeague dataLeague = response.body();
                if (dataLeague != null) {
                    List<Chart> charts = dataLeague.getDataIdLeague().getInforLeague().getCharts();
                    chartML.postValue(charts);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataLeague> call, @NonNull Throwable t) {
                Log.i("Dunno", "Chart: " + t);
            }
        });
    }
}
