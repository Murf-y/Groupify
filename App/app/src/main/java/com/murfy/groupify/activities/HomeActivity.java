package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.murfy.groupify.R;
import com.murfy.groupify.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private boolean showingMyGroups = true;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.mygroups.setOnClickListener(view -> changeSection(view, true));
        binding.explore.setOnClickListener(view -> changeSection(view, false));

        binding.searchButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
        });
        binding.addButton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), CreateGroupActivity.class);
            startActivity(i);
        });

    }

    public void changeSection(View v, boolean shouldShowMyGroups){
        if(!showingMyGroups && shouldShowMyGroups) {
            binding.explore.setTextColor(getResources().getColor(R.color.grey_200));
            binding.mygroups.setTextColor(getResources().getColor(R.color.dark_blue_700));
            showingMyGroups = true;
            updateListView();
        }
        if(showingMyGroups && !shouldShowMyGroups){
            binding.mygroups.setTextColor(getResources().getColor(R.color.grey_200));
            binding.explore.setTextColor(getResources().getColor(R.color.dark_blue_700));
            showingMyGroups = false;
            updateListView();
        }
    }

    private void updateListView() {
    }
}