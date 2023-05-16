package com.example.lichthidaubongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lichthidaubongda.R;
import com.example.lichthidaubongda.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initAction();
    }

    private void initAction() {
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, MainActivity.class)), 2100);
    }

    private void initView() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_alpha_2);
        binding.imgLogo.startAnimation(animation);
    }
}