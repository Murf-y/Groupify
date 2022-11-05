package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.murfy.groupify.R;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupMembersApi;
import com.murfy.groupify.databinding.ActivityGroupDescriptionBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;

public class GroupDescriptionActivity extends AppCompatActivity {

    User currentUser;
    Group currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupDescriptionBinding binding = ActivityGroupDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new GroupMembersApi(getApplicationContext()).isMemberInGroup(currentUser.getId(), currentGroup.getId(), new CrudCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean is_in_group) {
                if (!is_in_group) {
                    binding.joinOrLeaveButton.setText("JOIN");
                    binding.joinOrLeaveButton.setBackground(getDrawable(R.drawable.button));
                    binding.joinOrLeaveButton.setOnClickListener(view -> {
                        new GroupMembersApi(getApplicationContext()).joinGroup(currentUser.getId(), currentGroup.getId(), new CrudCallback<Object>(){
                            @Override
                            public void onSuccess(Object o) {
                                finish();
                            }

                            @Override
                            public void onError(CrudError error) {
                                finish();
                            }
                        });
                    });
                } else {
                    binding.joinOrLeaveButton.setText("LEAVE");
                    binding.joinOrLeaveButton.setBackground(getDrawable(R.drawable.button_red));
                    binding.joinOrLeaveButton.setOnClickListener(view -> {
                        new GroupMembersApi(getApplicationContext()).leaveGroup(currentUser.getId(), currentGroup.getId(), new CrudCallback<Object>(){
                            @Override
                            public void onSuccess(Object o) {
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onError(CrudError error) {
                                finish();
                            }
                        });
                    });
                }
            }
            @Override
            public void onError(CrudError error) {
                finish();
            }
        });
        binding.backArrow3.setOnClickListener(view -> {
            finish();
        });
    }
}