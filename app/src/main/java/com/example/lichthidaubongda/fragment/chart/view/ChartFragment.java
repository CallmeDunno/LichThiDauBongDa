package com.example.lichthidaubongda.fragment.chart.view;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lichthidaubongda.R;
import com.example.lichthidaubongda.databinding.FragmentChartBinding;
import com.example.lichthidaubongda.fragment.chart.adapter.ChartAdapter;
import com.example.lichthidaubongda.fragment.chart.model.Chart;
import com.example.lichthidaubongda.fragment.chart.viewmodel.ChartViewModel;

public class ChartFragment extends Fragment {
    private FragmentChartBinding binding;
    private ChartAdapter chartAdapter;
    private ChartViewModel viewModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
    }

    private void initAction() {
        chartAdapter.setOnClickItemChart(this::handleItemClick);
    }

    private void handleItemClick(Chart chart) {
        showStatDialog(chart);
    }

    private void showStatDialog(Chart chart) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_stat_detail);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTeamNameDialogChart = dialog.findViewById(R.id.tv_team_name_dialog_chart);
        TextView tvWin = dialog.findViewById(R.id.tv_win);
        TextView tvDraw = dialog.findViewById(R.id.tv_draw);
        TextView tvLose = dialog.findViewById(R.id.tv_lose);
        TextView tvScoredGoal = dialog.findViewById(R.id.tv_scored_goal);
        TextView tvConcededGoal = dialog.findViewById(R.id.tv_conceded_goal);
        TextView tvMatchesPlayed = dialog.findViewById(R.id.tv_matches_played);
        TextView tvGoalDifference = dialog.findViewById(R.id.tv_goal_difference);
        TextView tvTotalScore = dialog.findViewById(R.id.tv_total_score);
        TextView tvRank = dialog.findViewById(R.id.tv_rank);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);

        tvTeamNameDialogChart.setText(chart.getTeamName());
        tvWin.setText(String.valueOf(chart.getStat().getWin()));
        tvDraw.setText(String.valueOf(chart.getStat().getDraw()));
        tvLose.setText(String.valueOf(chart.getStat().getLose()));
        tvScoredGoal.setText(String.valueOf(chart.getStat().getGoalsFor()));
        tvConcededGoal.setText(String.valueOf(chart.getStat().getGoalsAgainst()));
        tvMatchesPlayed.setText(String.valueOf(chart.getStat().getMatchesPlayed()));
        tvGoalDifference.setText(String.valueOf(chart.getGoalsDiff()));
        tvTotalScore.setText(String.valueOf(chart.getPoints()));
        tvRank.setText(String.valueOf(chart.getRank()));
        btnConfirm.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void initView() {
        progressDialog = new ProgressDialog(requireContext());
        initAdapter();
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ChartViewModel.class);
        viewModel.getChartML().observe(requireActivity(), charts -> {
            if (charts == null){
                progressDialog.show();
            } else {
                chartAdapter.submitList(charts);
                progressDialog.dismiss();
            }
        });
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.rcvChart.setLayoutManager(layoutManager);
        chartAdapter = new ChartAdapter();
        binding.rcvChart.setAdapter(chartAdapter);
    }
}