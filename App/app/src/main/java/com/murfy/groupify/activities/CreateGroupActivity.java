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

import com.murfy.groupify.databinding.ActivityCreateGroupBinding;
import com.murfy.groupify.utils.ImageEncoding;

import java.io.IOException;

public class CreateGroupActivity extends AppCompatActivity {

    Bitmap imageBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreateGroupBinding binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
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
            String name = binding.groupNameInput.getText().toString();
            String description = binding.groupDescriptionInput.getText().toString();

            Log.i("DEBUG", name + " " + description);
        });
    }
}