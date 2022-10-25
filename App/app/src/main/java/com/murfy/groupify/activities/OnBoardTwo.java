package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.murfy.groupify.databinding.ActivityOnBoardTwoBinding;

public class OnBoardTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardTwoBinding binding = ActivityOnBoardTwoBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

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