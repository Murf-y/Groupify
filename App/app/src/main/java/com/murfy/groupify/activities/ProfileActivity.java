package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.adapters.GroupAdapter;
import com.murfy.groupify.adapters.NotificationAdapter;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupApi;
import com.murfy.groupify.api.NotificationApi;
import com.murfy.groupify.databinding.ActivityProfileBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.Notification;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.ListViewHelper;

import java.io.Serializable;
import java.util.ArrayList;

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
            new GroupApi(getApplicationContext()).getGroupsCreatedByMe(currentUser.getId(), new CrudCallback<ArrayList<Group>>() {
                @Override
                public void onSuccess(ArrayList<Group> groups) {
                    binding.listView.setAdapter(new GroupAdapter(getApplicationContext(), R.layout.group_list_item, groups));
                    ListViewHelper.getListViewSize(binding.listView);
                }

                @Override
                public void onError(CrudError error) {
                    ArrayList<String> err = new ArrayList<String>();
                    err.add(error.getMessage());
                    binding.listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, err));
                }
            });
        }else{
            new NotificationApi(getApplicationContext()).getUserNotifications(currentUser.getId(), new CrudCallback<ArrayList<Notification>>(){
                @Override
                public void onSuccess(ArrayList<Notification> notifications) {
                    ArrayList<Notification> notifications_l = new ArrayList<Notification>();
                    notifications_l.add(new Notification("1", null, null, "This is a notification", "2022-10-12"));
                    binding.listView.setAdapter(new NotificationAdapter(getApplicationContext(), R.layout.notification_list_item, notifications_l));
                    ListViewHelper.getListViewSize(binding.listView);
                }
                @Override
                public void onError(CrudError error){
                    ArrayList<String> err = new ArrayList<String>();
                    err.add(error.getMessage());
                    binding.listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, err));
                }
            });
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