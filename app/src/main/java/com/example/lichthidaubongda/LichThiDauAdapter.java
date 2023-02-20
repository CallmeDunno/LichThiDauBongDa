package com.example.lichthidaubongda;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class LichThiDauAdapter extends BaseAdapter {

    private final Activity context;
    private final int layout;
    private final List<LichThiDau> danhSachTD;

    public LichThiDauAdapter(Activity context, int layout, List<LichThiDau> danhSachTD) {
        this.context = context;
        this.layout = layout;
        this.danhSachTD = danhSachTD;
    }

    @Override
    public int getCount() {
        return danhSachTD.size();
    }

    @Override
    public Object getItem(int i) {
        return danhSachTD.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder.img_logo1 = view.findViewById(R.id.img_t1);
            viewHolder.img_logo2 = view.findViewById(R.id.img_t2);
            viewHolder.tv_doi1 = view.findViewById(R.id.tv_team1);
            viewHolder.tv_doi2 = view.findViewById(R.id.tv_team2);
            viewHolder.tv_vongdau = view.findViewById(R.id.tv_round);
            viewHolder.tv_giodau = view.findViewById(R.id.tv_time);
            viewHolder.tv_ngaythidau = view.findViewById(R.id.tv_date);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(context).load(danhSachTD.get(i).getLogo_1()).into(viewHolder.img_logo1);
        Glide.with(context).load(danhSachTD.get(i).getLogo_2()).into(viewHolder.img_logo2);
        viewHolder.tv_doi1.setText(danhSachTD.get(i).getTeam_1());
        viewHolder.tv_doi2.setText(danhSachTD.get(i).getTeam_2());
        viewHolder.tv_vongdau.setText(danhSachTD.get(i).getRound());
        String[] dateTime = danhSachTD.get(i).getDateTime().toString().split("\\s");
        viewHolder.tv_ngaythidau.setText(dateTime[0]);
        viewHolder.tv_giodau.setText(dateTime[1]);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_list_item);
        view.startAnimation(animation);

        return view;
    }

    private static class ViewHolder{
        ImageView img_logo1, img_logo2;
        TextView tv_doi1, tv_doi2, tv_vongdau, tv_ngaythidau, tv_giodau;
    }
}
