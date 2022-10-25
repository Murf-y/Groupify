package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.R;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivityLoginBinding;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.Delayer;
import com.murfy.groupify.utils.InputValidator;

public class Login extends AppCompatActivity {

    boolean show_pass = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
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
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            if(!InputValidator.isEmailValid(email)){
                binding.errorMessage.setText("Invalid format for email");
                binding.errorMessage.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().fadeIn(binding.errorMessage, 1000);
                Delayer.getInstance().postAfter(() -> {
                    AnimationHelper.getInstance().fadeOut(binding.errorMessage, 1000);
                    Delayer.getInstance().postAfter(() -> {
                        binding.errorMessage.setVisibility(View.GONE);
                        return null;
                    }, 1000);
                    return null;
                }, 5000);
            }else if(!InputValidator.isPasswordValid(password)){
                binding.errorMessage.setText("Password must be at least 8 characters");
                binding.errorMessage.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().fadeIn(binding.errorMessage, 1000);
                Delayer.getInstance().postAfter(() -> {
                    AnimationHelper.getInstance().fadeOut(binding.errorMessage, 1000);
                    Delayer.getInstance().postAfter(() -> {
                        binding.errorMessage.setVisibility(View.GONE);
                        return null;
                    }, 1000);
                    return null;
                }, 5000);
            }
            else{
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }

            // proceed with login procedure

        });

        binding.signupInstead.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Signup.class);
            startActivity(i);
        });
    }
}