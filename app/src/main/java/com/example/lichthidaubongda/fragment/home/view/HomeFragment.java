package com.example.lichthidaubongda.fragment.home.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.lichthidaubongda.R;
import com.example.lichthidaubongda.databinding.FragmentHomeBinding;
import com.example.lichthidaubongda.fragment.home.viewmodel.HomeViewModel;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
    }

    private void initAction() {
        binding.btnSchedule.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.scheduleFragment));
        binding.btnChart.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.chartFragment));
        binding.btnAbout.setOnClickListener(this::showAboutDialog);
        binding.btnContact.setOnClickListener(this::showContactDialog);
    }

    private void showContactDialog(View view) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_contact_home);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnConfirmContactHome = dialog.findViewById(R.id.btn_confirm_contact_home);

        btnConfirmContactHome.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showAboutDialog(View view) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about_home);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnConfirmAboutHome = dialog.findViewById(R.id.btn_confirm_about_home);

        btnConfirmAboutHome.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void initView() {
        progressDialog = new ProgressDialog(requireContext());
        initViewModel();
    }

    @SuppressLint("SetTextI18n")
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getLeague().observe(requireActivity(), league -> {
            if (league == null){
                progressDialog.show();
            } else {
                Glide.with(requireContext()).load(league.getLogo()).into(binding.imgLeagueLogo);
                binding.tvLeagueName.setText(league.getName());
                binding.tvLeagueSeason.setText("Season: " + league.getSeason());
                binding.tvLeagueSeasonStart.setText("Start: " + league.getSeasonStart());
                binding.tvLeagueSeasonEnd.setText("End: " + league.getSeasonEnd());
                progressDialog.dismiss();
            }
        });
    }
}