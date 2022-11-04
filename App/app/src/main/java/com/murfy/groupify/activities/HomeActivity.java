package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.adapters.GroupAdapter;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupApi;
import com.murfy.groupify.databinding.ActivityHomeBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.ImageEncoding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private boolean showingMyGroups = true;
    private ActivityHomeBinding binding;


    private User currentUser;
    private ArrayList<Group> groups_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        binding.avatar.setImageBitmap(currentUser.getProfilePhoto());
        binding.avatar.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);
        });

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


        updateListView();
        binding.groupsListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
            intent.putExtra("group", groups_list.get(i));
            startActivity(intent);
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
        CrudCallback<ArrayList<Group>> callback = new CrudCallback<ArrayList<Group>>() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                groups_list = groups;
                if(groups.size() == 0){
                    binding.emptyStateText.setVisibility(View.VISIBLE);
                    binding.emptyStateImage.setVisibility(View.VISIBLE);
                    binding.groupsListView.setVisibility(View.GONE);
                }else{
                    binding.emptyStateText.setVisibility(View.GONE);
                    binding.emptyStateImage.setVisibility(View.GONE);
                    binding.groupsListView.setVisibility(View.VISIBLE);
                    binding.groupsListView.setAdapter(new GroupAdapter(getApplicationContext(), R.layout.group_list_item, groups));
                }
            }
            @Override
            public void onError(CrudError error) {
                binding.groupsListView.setVisibility(View.GONE);
                binding.emptyStateText.setVisibility(View.VISIBLE);
                binding.emptyStateText.setText(error.getMessage());
            }
        };
        if(showingMyGroups){
            new GroupApi(getApplicationContext()).getMyGroups(currentUser.getId(), callback);
        }else{
            new GroupApi(getApplicationContext()).getAllGroups(callback);
        }
    }
}