package com.example.lichthidaubongda.fragment.chart.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lichthidaubongda.R;
import com.example.lichthidaubongda.databinding.ItemRcvChartBinding;
import com.example.lichthidaubongda.fragment.chart.interfaces.IClickItemChart;
import com.example.lichthidaubongda.fragment.chart.model.Chart;

public class ChartAdapter extends ListAdapter<Chart, ChartAdapter.ChartViewHolder> {
    private IClickItemChart iClickItemChart;

    public ChartAdapter() {
        super(Chart.CHART_DIFF_UTIL);
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvChartBinding itemRcvChartBinding = ItemRcvChartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChartViewHolder(itemRcvChartBinding, iClickItemChart);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        if (getItem(position) == null) {
            return;
        }
        holder.bindData(getItem(position));
    }

    public void setOnClickItemChart(IClickItemChart itemChart) {
        this.iClickItemChart = itemChart;
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder {
        private final ItemRcvChartBinding itemBinding;
        private Chart chart = null;

        public ChartViewHolder(ItemRcvChartBinding itemBinding, IClickItemChart iClickItemChart) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.getRoot().setOnClickListener(view -> {
                if (chart != null) {
                    iClickItemChart.onClickItemChartSelected(chart);
                }
            });
        }

        public void bindData(Chart item) {
            this.chart = item;
            Glide.with(itemView.getContext()).load(item.getLogo()).into(itemBinding.imgItemChart);
            itemBinding.tvNameItemChart.setText(item.getTeamName());
            itemBinding.tvNumberItemChart.setText(String.valueOf(item.getRank()));

            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.translate_alpha);
            itemView.startAnimation(animation);
        }
    }

}
