package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.databinding.ActivityOnBoardOneBinding;

public class OnBoardOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardOneBinding binding = ActivityOnBoardOneBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.onBoardOneNext.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), OnBoardTwo.class);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });

        binding.skip.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), OnBoardThree.class);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }
}