package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.murfy.groupify.databinding.ActivityCreatePostBinding;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreatePostBinding binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}