package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.murfy.groupify.databinding.ActivityOnBoardThreeBinding;

public class OnBoardThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardThreeBinding binding = ActivityOnBoardThreeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.getStartedBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Signup.class);
            startActivity(i);
        });
        binding.loginInstead.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
    }
}