package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.adapters.PostAdapter;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupMembersApi;
import com.murfy.groupify.api.PostApi;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivityGroupBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.Postable;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.ImageEncoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class GroupActivity extends AppCompatActivity {

    Group group;
    User currentUser;
    ActivityGroupBinding binding;
    boolean send_message = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        group = (Group) getIntent().getSerializableExtra("group");
        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        new GroupMembersApi(getApplicationContext()).isMemberInGroup(currentUser.getId(), group.getId(), new CrudCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean is_in_group) {
                if(!is_in_group){
                    binding.messageInput.setText("Join group to send messages");
                    binding.messageInput.setClickable(false);
                    binding.messageInput.setEnabled(false);
                    binding.messageInput.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }else{
                    binding.messageInput.setText("");
                    binding.messageInput.setClickable(true);
                    binding.messageInput.setEnabled(true);
                    binding.messageInput.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.add_post_icon,0);
                }
            }

            @Override
            public void onError(CrudError error) {
                binding.messageInput.setText("Join group to send messages");
                binding.messageInput.setClickable(false);
                binding.messageInput.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            }
        });

        binding.goBackFromGroup.setOnClickListener(view -> {
            finish();
        });

        binding.groupName.setText(group.getSubject());
        binding.membersCount.setText(group.getNumberOfMembers());
        binding.groupPhoto.setImageBitmap(group.getPhoto());

        binding.messageInput.setDrawableClickListener(target -> {
            if(target == DrawableClickListener.DrawablePosition.RIGHT){
                if(send_message) sendMessage();
                else sendPost();
            }
        });
        binding.messageInput.setOnKeyListener((view, i, keyEvent) -> {
            if(binding.messageInput.getText().toString().trim().length() > 0){
                binding.messageInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.send_message_icon,0);
                send_message = true;
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    sendMessage();
                }
            }else{
                send_message = false;
                binding.messageInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_post_icon,0);
            }
            return false;
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               updateMessage();
            }
        }, 0, 5000);

        binding.groupPhoto.setOnClickListener(view -> {
            goToGroupDescription();
        });
        binding.nameAndCount.setOnClickListener(view -> {
            goToGroupDescription();
        });
    }

    private void updateMessage(){
        new PostApi(getApplicationContext()).getPostables(group.getId(), new CrudCallback<ArrayList<Postable>>() {
            @Override
            public void onSuccess(ArrayList<Postable> postables) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    postables.sort(new Comparator<Postable>() {
                        @Override
                        public int compare(Postable t2, Postable t1) {
                            return t2.getCreated_at().compareTo(t1.getCreated_at());
                        }
                    });
                }
                binding.postablesList.setAdapter(new PostAdapter(getApplicationContext(), postables));
                binding.postablesList.setSelection(postables.size() - 1);
                //binding.postablesList.smoothScrollByOffset(binding.postablesList.getMaxScrollAmount());
            }

            @Override
            public void onError(CrudError error) {
                return;
            }
        });
    }

    private void goToGroupDescription(){
        Intent i = new Intent(getApplicationContext(), GroupDescriptionActivity.class);
        i.putExtra("group", group);
        startActivity(i);
    }
    private void sendMessage() {
        new PostApi(getApplicationContext()).postMessage(group.getId(), currentUser.getId(), binding.messageInput.getText().toString(), new CrudCallback<Object>() {
            public void onSuccess(Object obj){
                binding.messageInput.setText("");
                send_message = false;
                binding.messageInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.add_post_icon,0);
                updateMessage();
                return;
            }
            public void onError(CrudError error){
                return;
            }
        });
    }

    private void sendPost(){
        Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
        i.putExtra("group", group);
        startActivity(i);
    }
}