package com.murfy.groupify.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.GroupApi;
import com.murfy.groupify.databinding.ActivityCreateGroupBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.ImageEncoding;

import java.io.IOException;

public class CreateGroupActivity extends AppCompatActivity {

    Bitmap imageBitMap;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateGroupBinding binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result != null) {
                        Uri imageUri = result.getData() != null ? result.getData().getData() : null;
                        try {
                            imageBitMap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                            binding.addImageButton.setVisibility(View.GONE);
                            binding.addImageLabel.setVisibility(View.GONE);
                            binding.imagePreview.setImageURI(imageUri);
                            binding.imagePreview.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        binding.backArrow.setOnClickListener(view -> {
            finish();
        });

        binding.addImageButton.setOnClickListener(view -> {
            Intent pickIntent = new Intent();
            pickIntent.setType("image/*");
            pickIntent.setAction(Intent.ACTION_GET_CONTENT);

            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String pickTitle = "Select or take a new Picture";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
            chooserIntent.putExtra
                    (
                            Intent.EXTRA_INITIAL_INTENTS,
                            new Intent[] { takePhotoIntent }
                    );

            imagePickerActivityResult.launch(pickIntent);
        });

        binding.cancelBtn.setOnClickListener(view -> {
            finish();
        });
        binding.createBtn.setOnClickListener(view -> {
            String subject = binding.groupNameInput.getText().toString();
            String description = binding.groupDescriptionInput.getText().toString();
            String group_photo =
                    ImageEncoding.convertToBase64(imageBitMap == null ?
                            ImageEncoding.getBitmapFromXml(getDrawable(R.drawable.group_default_image)):
                            imageBitMap);
            new GroupApi(getApplicationContext()).createGroup(currentUser.getId(), subject, description, group_photo, new CrudCallback<Group>() {
                @Override
                public void onSuccess(Group group) {
                    binding.createGroupError.setVisibility(View.GONE);
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }

                @Override
                public void onError(CrudError error) {
                    binding.createGroupError.setVisibility(View.VISIBLE);
                }
            });

        });
    }
}