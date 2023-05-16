package com.example.lichthidaubongda.fragment.schedule.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lichthidaubongda.fragment.schedule.model.Schedule;
import com.example.lichthidaubongda.fragment.schedule.repository.ScheduleRepository;

import java.util.List;

public class ScheduleViewModel extends ViewModel {
    private final ScheduleRepository repository;

    public ScheduleViewModel() {
        repository = new ScheduleRepository();
    }

    public MutableLiveData<List<Schedule>> getSchedulesAllMatchesML() {
        return repository.getSchedulesAllMatchesML();
    }

    public MutableLiveData<List<Schedule>> getSchedulesNSMatchesML() {
        return repository.getSchedulesNSMatchesML();
    }
}

