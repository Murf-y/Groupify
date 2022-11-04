package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.murfy.groupify.databinding.ActivityProfileBinding;
import com.murfy.groupify.models.User;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO : if intent has extra, passed to it, i.e. if we pass user to the intent, then we are checking other users profile not ours

        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        binding.pic.setImageBitmap(currentUser.getProfilePhoto());
        binding.username.setText(currentUser.getUsername());
        binding.bio.setText(currentUser.getBio());

        binding.profileBackArrow.setOnClickListener(view -> {
            finish();
        });
    }
}