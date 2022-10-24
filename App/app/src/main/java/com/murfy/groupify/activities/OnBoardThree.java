package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}