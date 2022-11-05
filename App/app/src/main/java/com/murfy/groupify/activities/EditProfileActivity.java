package com.murfy.groupify.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.UserApi;
import com.murfy.groupify.databinding.ActivityEditProfileBinding;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.ImageEncoding;
import com.murfy.groupify.utils.PermissionHelper;

public class EditProfileActivity extends AppCompatActivity {

    Bitmap imageBitMap;
    ActivityEditProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User currentUser = new Gson().fromJson(getSharedPreferences("groupify", MODE_PRIVATE).getString("current_user", "{}"), User.class);

        binding.profilePhotoEdit.setImageBitmap(currentUser.getProfilePhoto());
        binding.backArrow.setOnClickListener(view -> {
            finish();
        });

        binding.cancelBtn.setOnClickListener(view -> {
            finish();
        });

        binding.changeProfileImageBtn.setOnClickListener(view -> {
            if(PermissionHelper.checkAndRequestPermissions(this)){
                chooseImage(this);
            }
        });

        binding.updateBtn.setOnClickListener(view -> {
            String username = binding.usernameInput.getText().toString();
            String bio = binding.bioInput.getText().toString();
            String profile_photo =
                    ImageEncoding.convertToBase64(imageBitMap == null ?
                            currentUser.getProfilePhoto() :
                            imageBitMap);

            if(profile_photo.equals(currentUser.getPorfilePhotoBase64()) && username.length() == 0 && bio.length() == 0){
                binding.updateProfileError.setText("At least 1 field should change to update it");
                binding.updateProfileError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.updateProfileError);
                return;
            }
            if(username.length() == 0) username = currentUser.getUsername();
            if(bio.length() == 0) bio = currentUser.getBio();

            new UserApi(getApplicationContext()).updateUser(currentUser.getId(), username, bio, profile_photo, new CrudCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    SharedPreferences shared = getSharedPreferences("groupify", MODE_PRIVATE);
                    shared.edit().putString("current_user", new Gson().toJson(user)).apply();

                    Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(i);
                }

                @Override
                public void onError(CrudError error) {
                    binding.updateProfileError.setText(error.getMessage());
                    binding.updateProfileError.setVisibility(View.VISIBLE);
                    AnimationHelper.getInstance().animateError(binding.updateProfileError);
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
                        binding.profilePhotoEdit.setImageBitmap(imageBitMap);
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
                                binding.profilePhotoEdit.setImageBitmap(imageBitMap);
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}