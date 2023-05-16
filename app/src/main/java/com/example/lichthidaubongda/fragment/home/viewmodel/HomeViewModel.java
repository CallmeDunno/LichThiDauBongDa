package com.example.lichthidaubongda.fragment.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lichthidaubongda.fragment.home.model.League;
import com.example.lichthidaubongda.fragment.home.repository.HomeRepository;

public class HomeViewModel extends ViewModel {
    private final HomeRepository repository;

    public HomeViewModel(){
        repository = new HomeRepository();
    }

    public MutableLiveData<League> getLeague(){
        return repository.getLeagueML();
    }
}
