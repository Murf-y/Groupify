package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.murfy.groupify.databinding.ActivityGroupDescriptionBinding;

public class GroupDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupDescriptionBinding binding = ActivityGroupDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}