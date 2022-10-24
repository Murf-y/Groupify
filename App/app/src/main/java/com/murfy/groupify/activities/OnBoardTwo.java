package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}