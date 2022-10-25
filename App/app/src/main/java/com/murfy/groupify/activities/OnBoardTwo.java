package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.databinding.ActivityOnBoardTwoBinding;

public class OnBoardTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardTwoBinding binding = ActivityOnBoardTwoBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.onBoardTwoNext.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), OnBoardThree.class);
            startActivity(i);
        });

        binding.skip.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), OnBoardThree.class);
            startActivity(i);
        });
    }
}