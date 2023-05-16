package com.example.lichthidaubongda.fragment.chart.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lichthidaubongda.fragment.chart.model.Chart;
import com.example.lichthidaubongda.fragment.chart.repository.ChartRepository;

import java.util.List;

public class ChartViewModel extends ViewModel {

    private ChartRepository repository;

    public ChartViewModel(){
        repository = new ChartRepository();
    }

    public MutableLiveData<List<Chart>> getChartML(){
        return repository.getChartML();
    }

}
