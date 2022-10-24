package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.murfy.groupify.R;
import com.murfy.groupify.databinding.ActivityOnBoardOneBinding;
import com.murfy.groupify.databinding.ActivitySplashScreenBinding;

public class OnBoardOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardOneBinding binding = ActivityOnBoardOneBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
    }
}