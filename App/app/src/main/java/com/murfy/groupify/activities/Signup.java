package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.R;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivitySignupBinding;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.Delayer;
import com.murfy.groupify.utils.InputValidator;

public class Signup extends AppCompatActivity {

    boolean show_pass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

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

                Delayer.getInstance().postAfter(() -> {
                    AnimationHelper.getInstance().fadeOut(binding.usernameError, 1000);
                    binding.usernameError.setVisibility(View.GONE);
                    return null;
                }, 5000);
            }else if(!InputValidator.isEmailValid(email)){
                binding.usernameError.setText("Invalid format for email");
                binding.usernameError.setVisibility(View.VISIBLE);

                Delayer.getInstance().postAfter(() -> {
                    AnimationHelper.getInstance().fadeOut(binding.usernameError, 1000);
                    binding.usernameError.setVisibility(View.GONE);
                    return null;
                }, 5000);
            }else if(!InputValidator.isPasswordValid(password)){
                binding.usernameError.setText("Invalid format for password");
                binding.usernameError.setVisibility(View.VISIBLE);

                Delayer.getInstance().postAfter(() -> {
                    AnimationHelper.getInstance().fadeOut(binding.usernameError, 1000);
                    binding.usernameError.setVisibility(View.GONE);
                    return null;
                }, 5000);
            }

            // proceed with sign up procedure

        });

        binding.loginInstead.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
    }
}