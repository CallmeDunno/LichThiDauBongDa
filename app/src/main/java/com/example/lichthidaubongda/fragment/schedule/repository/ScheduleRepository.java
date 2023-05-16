package com.example.lichthidaubongda.fragment.schedule.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lichthidaubongda.fragment.schedule.api.ApiSchedule;
import com.example.lichthidaubongda.fragment.schedule.model.DataSchedules;
import com.example.lichthidaubongda.fragment.schedule.model.Schedule;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleRepository {
    private MutableLiveData<List<Schedule>> schedulesAllMatchesML;
    private MutableLiveData<List<Schedule>> schedulesNSMatchesML;

    private List<Schedule> schedules = new ArrayList<>();

    public ScheduleRepository() {
        this.schedulesAllMatchesML = new MutableLiveData<>();
        this.schedulesNSMatchesML = new MutableLiveData<>();
    }

    public MutableLiveData<List<Schedule>> getSchedulesAllMatchesML() {
        setSchedulesAllMatchesML();
        return schedulesAllMatchesML;
    }

    public MutableLiveData<List<Schedule>> getSchedulesNSMatchesML() {
        setSchedulesNSMatchesML();
        return schedulesAllMatchesML;
    }

    private void setSchedulesAllMatchesML(){
        ApiSchedule.apiSchedule.getListSchedule().enqueue(new Callback<DataSchedules>() {
            @Override
            public void onResponse(@NonNull Call<DataSchedules> call, @NonNull Response<DataSchedules> response) {
                Log.e("Dunno", "Schedule: Success");
                DataSchedules dataSchedules = response.body();
                if (dataSchedules != null){
                    schedules = dataSchedules.getDataIdSchedule().getIdSchedules().getSchedules();
                    schedulesAllMatchesML.postValue(schedules);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSchedules> call, @NonNull Throwable t) {
                Log.e("Dunno", "Schedule: " + t);
            }
        });
        schedulesAllMatchesML.setValue(schedules);
    }

    private void setSchedulesNSMatchesML(){
        ApiSchedule.apiSchedule.getListSchedule().enqueue(new Callback<DataSchedules>() {
            @Override
            public void onResponse(@NonNull Call<DataSchedules> call, @NonNull Response<DataSchedules> response) {
                Log.e("Dunno", "Schedule: Success");
                DataSchedules dataSchedules = response.body();
                if (dataSchedules != null){
                    schedules = dataSchedules.getDataIdSchedule().getIdSchedules().getSchedules();
                    List<Schedule> list = new ArrayList<>();
                    for (Schedule s : schedules){
                        if (s.getStatus().equals("NS")){
                            list.add(s);
                        }
                    }
                    schedulesAllMatchesML.postValue(list);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSchedules> call, @NonNull Throwable t) {
                Log.e("Dunno", "Schedule: " + t);
            }
        });
        schedulesAllMatchesML.setValue(schedules);
    }

}
