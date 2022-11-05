package com.murfy.groupify.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.PostApi;
import com.murfy.groupify.databinding.ActivityCreatePostBinding;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.ImageEncoding;
import com.murfy.groupify.utils.PermissionHelper;

public class CreatePostActivity extends AppCompatActivity {

    Bitmap imageBitMap;
    User currentUser;
    Group currentGroup;
    ActivityCreatePostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);
        currentGroup = (Group) getIntent().getSerializableExtra("group");

        binding.backArrow.setOnClickListener(view -> {
            finish();
        });

        binding.addImageButton.setOnClickListener(view -> {
            if(PermissionHelper.checkAndRequestPermissions(this)){
                chooseImage(this);
            }
        });

        binding.cancelBtn.setOnClickListener(view -> {
            finish();
        });

        binding.postBtn.setOnClickListener(view -> {
            String title = binding.postTitleInput.getText().toString();
            String content = binding.postContentInput.getText().toString();
            if(title.length() < 1){
                binding.createPostError.setText("Post title should be at least 1 char long");
                binding.createPostError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.createPostError);
                return;
            }else if(content.length() <= 1){
                binding.createPostError.setText("Post content should be at least 1 char long");
                binding.createPostError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.createPostError);
                return;
            }else if(imageBitMap == null){
                binding.createPostError.setText("Posts should have an image! otherwise use a message");
                binding.createPostError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.createPostError);
                return;
            }

            new PostApi(getApplicationContext()).postPost(currentGroup.getId(), currentUser.getId(), title, content, ImageEncoding.convertToBase64(imageBitMap), new CrudCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    finish();
                }

                @Override
                public void onError(CrudError error) {
                    binding.createPostError.setText(error.getMessage());
                    binding.createPostError.setVisibility(View.VISIBLE);
                    AnimationHelper.getInstance().animateError(binding.createPostError);
                    return;
                }
            });
        });


    }

    private void chooseImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(this);
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        imageBitMap = (Bitmap) data.getExtras().get("data");
                        binding.addImageButton.setVisibility(View.GONE);
                        binding.addImageLabel.setVisibility(View.GONE);
                        binding.imagePreview.setImageBitmap(imageBitMap);
                        binding.imagePreview.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageBitMap = BitmapFactory.decodeFile(picturePath);
                                binding.addImageButton.setVisibility(View.GONE);
                                binding.addImageLabel.setVisibility(View.GONE);
                                binding.imagePreview.setImageBitmap(imageBitMap);
                                binding.imagePreview.setVisibility(View.VISIBLE);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}