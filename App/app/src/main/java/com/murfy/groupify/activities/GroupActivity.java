package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.murfy.groupify.R;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivityGroupBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.utils.ImageEncoding;

public class GroupActivity extends AppCompatActivity {

    Group group;
    boolean send_message = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGroupBinding binding = ActivityGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        group = (Group) getIntent().getSerializableExtra("group");

        binding.backArrow2.setOnClickListener(view -> {
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


        // todo get messages and display them
        binding.groupPhoto.setOnClickListener(view -> {
            goToGroupDescription();
        });
        binding.nameAndCount.setOnClickListener(view -> {
            goToGroupDescription();
        });
    }

    private void goToGroupDescription(){
        Intent i = new Intent(getApplicationContext(), GroupDescriptionActivity.class);
        i.putExtra("group", group);
        startActivity(i);
    }
    private void sendMessage() {
    }

    private void sendPost(){

    }
}