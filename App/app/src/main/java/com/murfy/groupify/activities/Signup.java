package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.UserApi;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivitySignupBinding;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.InputValidator;

public class Signup extends AppCompatActivity {

    boolean show_pass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.passwordInput.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                if(target == DrawablePosition.RIGHT){
                    show_pass = !show_pass;
                    binding.passwordInput.setTransformationMethod(show_pass ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    binding.passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, show_pass ? R.drawable.signup_eye_closed : R.drawable.signup_eye,0);
                }
            }
        });

        binding.signup.setOnClickListener(view -> {
            String username = binding.usernameInput.getText().toString();
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            if(username.length() <=  0){
                binding.usernameError.setText("Username should not be empty!");
                binding.usernameError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.usernameError);
            }else if(!InputValidator.isEmailValid(email)){
                binding.accountinfoError.setText("Invalid format for email");
                binding.accountinfoError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.accountinfoError);
            }else if(!InputValidator.isPasswordValid(password)){
                binding.accountinfoError.setText("Password must be at least 8 characters");
                binding.accountinfoError.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.accountinfoError);
            }else{
                new UserApi(getApplicationContext()).signUp(username, email, password, new CrudCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        SharedPreferences shared = getSharedPreferences("groupify", MODE_PRIVATE);
                        shared.edit().putString("current_user", new Gson().toJson(user)).apply();

                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onError(CrudError error) {
                        if(error.getMessage().contains("username")){
                            binding.usernameError.setText(error.getMessage());
                            binding.usernameError.setVisibility(View.VISIBLE);
                            AnimationHelper.getInstance().animateError(binding.usernameError);
                        }else{
                            binding.accountinfoError.setText(error.getMessage());
                            binding.accountinfoError.setVisibility(View.VISIBLE);
                            AnimationHelper.getInstance().animateError(binding.accountinfoError);
                        }
                    }
                });
            }
        });

        binding.loginInstead.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
    }
}