package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.adapters.GroupMemberAdapter;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupMembersApi;
import com.murfy.groupify.databinding.ActivityGroupDescriptionBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;

import java.util.ArrayList;

public class GroupDescriptionActivity extends AppCompatActivity {

    User currentUser;
    Group currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupDescriptionBinding binding = ActivityGroupDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentGroup = (Group) getIntent().getSerializableExtra("group");
        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        new GroupMembersApi(getApplicationContext()).isMemberInGroup(currentUser.getId(), currentGroup.getId(), new CrudCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean is_in_group) {
                if (!is_in_group) {
                    binding.joinOrLeaveButton.setText("JOIN");
                    binding.joinOrLeaveButton.setBackground(getDrawable(R.drawable.button));
                    binding.joinOrLeaveButton.setOnClickListener(view -> {
                        new GroupMembersApi(getApplicationContext()).joinGroup(currentUser.getId(), currentGroup.getId(), new CrudCallback<Group>(){
                            @Override
                            public void onSuccess(Group group) {
                                Intent i = new Intent(getApplicationContext(), GroupActivity.class);
                                i.putExtra("group", group);
                                startActivity(i);
                            }

                            @Override
                            public void onError(CrudError error) {
                                finish();
                            }
                        });
                    });
                } else {
                    binding.joinOrLeaveButton.setText("LEAVE");
                    if(currentGroup.getOwner_id().equals(currentUser.getId())){
                        binding.joinOrLeaveButton.setBackground(getDrawable(R.drawable.disabled_button));
                    }
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

        new GroupMembersApi(getApplicationContext()).getGroupMembers(currentGroup.getId(), new CrudCallback<ArrayList<User>>(){
            @Override
            public void onSuccess(ArrayList<User> users) {
                binding.membersList.setAdapter(new GroupMemberAdapter(getApplicationContext(), R.layout.member_list_item, users));
                binding.membersList.setOnItemClickListener((adapterView, view, i, l) -> {
                    User user = users.get(i);
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    if(!currentUser.getId().equals(user.getId())) intent.putExtra("checking_user", user);
                    startActivity(intent);
                });
            }

            @Override
            public void onError(CrudError error) {
                ArrayList<String> list = new ArrayList<>();
                list.add(error.getMessage());
                binding.membersList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list));
            }
        });

        binding.backArrow3.setOnClickListener(view -> {
            finish();
        });
        binding.groupPhoto2.setImageBitmap(currentGroup.getPhoto());
        binding.description.setText(currentGroup.getDescription());
        binding.groupSubjectText.setText(currentGroup.getSubject());
        binding.memberCount.setText(currentGroup.getNumberOfMembers() + " . " + "members");
        binding.createAtText.setText("Created at " + currentGroup.getCreated_at().split(" ")[0]);
    }
}