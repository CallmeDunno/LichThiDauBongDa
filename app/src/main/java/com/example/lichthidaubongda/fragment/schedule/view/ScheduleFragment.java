package com.example.lichthidaubongda.fragment.schedule.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lichthidaubongda.databinding.FragmentScheduleBinding;
import com.example.lichthidaubongda.fragment.schedule.adapter.ScheduleAdapter;
import com.example.lichthidaubongda.fragment.schedule.viewmodel.ScheduleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private ScheduleViewModel viewModel;
    private ScheduleAdapter scheduleAdapter;
    private FragmentScheduleBinding binding;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
    }

    private void initAction() {
        binding.spSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        getSchedulesAllMatchesML();
                        break;
                    case 2:
                        getSchedulesNSMatchesML();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView() {
        progressDialog = new ProgressDialog(requireContext());
        initAdapter();
        initViewModel();
        initSpinner();
    }

    private void initSpinner() {
        List<String> listSpinner = new ArrayList<>();
        listSpinner.add("Filter");
        listSpinner.add("All matches");
        listSpinner.add("Not started");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listSpinner);
        binding.spSchedule.setAdapter(spinnerAdapter);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.rcvSchedule.setLayoutManager(linearLayoutManager);
        scheduleAdapter = new ScheduleAdapter();
        binding.rcvSchedule.setAdapter(scheduleAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        getSchedulesNSMatchesML();
    }

    private void getSchedulesAllMatchesML(){
        viewModel.getSchedulesAllMatchesML().observe(requireActivity(), schedules -> {
            if (schedules.size() == 0){
                progressDialog.show();
            } else {
                scheduleAdapter.submitList(schedules);
                progressDialog.dismiss();
            }
        });
    }

    private void getSchedulesNSMatchesML(){
        viewModel.getSchedulesNSMatchesML().observe(requireActivity(), schedules -> {
            if (schedules.size() == 0){
                progressDialog.show();
            } else {
                scheduleAdapter.submitList(schedules);
                progressDialog.dismiss();
            }
        });
    }

}