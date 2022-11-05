package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.databinding.ActivityProfileBinding;
import com.murfy.groupify.models.User;

import java.io.Serializable;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;
    boolean checkingMyself = false;
    boolean showingMyGroups = true;

    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Serializable checkingUser = getIntent().getSerializableExtra("checking_user");
        if(checkingUser == null){
            checkingMyself = true;
            currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);
            binding.filters.setVisibility(View.VISIBLE);
            binding.mygroups.setOnClickListener(view -> changeSection(view, true));
            binding.profileNotifications.setOnClickListener(view -> changeSection(view, false));
            binding.editIcon.setOnClickListener(view -> {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            });
        }else{
            checkingMyself = false;
            currentUser = (User) checkingUser;
            binding.filters.setVisibility(View.GONE);
            binding.frame2.setVisibility(View.GONE);
            binding.username.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        binding.pic.setImageBitmap(currentUser.getProfilePhoto());
        binding.username.setText(currentUser.getUsername());
        binding.bio.setText(currentUser.getBio());
        binding.numberOfGroups.setText(currentUser.getNumberOfGroupsJoined());
        binding.numberOfPosts.setText(currentUser.getNumberOfPosts());
        binding.profileBackArrow.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });
        updateListView();
    }

    private void updateListView(){
        if(showingMyGroups){

        }else{

        }
    }

    private void changeSection(View view, boolean shouldShowMyGroups) {
        if(!showingMyGroups && shouldShowMyGroups) {
            binding.profileNotifications.setTextColor(getResources().getColor(R.color.grey_200));
            binding.mygroups.setTextColor(getResources().getColor(R.color.dark_blue_700));
            showingMyGroups = true;
            updateListView();
        }

        if(showingMyGroups && !shouldShowMyGroups){
            binding.mygroups.setTextColor(getResources().getColor(R.color.grey_200));
            binding.profileNotifications.setTextColor(getResources().getColor(R.color.dark_blue_700));
            showingMyGroups = false;
            updateListView();
        }
    }
}