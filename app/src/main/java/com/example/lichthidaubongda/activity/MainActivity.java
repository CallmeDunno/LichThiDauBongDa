package com.example.lichthidaubongda.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lichthidaubongda.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Issue
     * - Khó có thể nâng cấp (xem lịch thi đấu các giải đấu khác) do gặp vấn đề về API, API trong project này được lấy của VnExpress
     * nên khó control được dữ liệu trả về
     * - Nhiều lịch thi đấu có cùng 1 thời gian => chưa biết cách dùng service để giải quyết vấn đề này.
     * */
}