package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.databinding.ActivityOnBoardThreeBinding;

public class OnBoardThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOnBoardThreeBinding binding = ActivityOnBoardThreeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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