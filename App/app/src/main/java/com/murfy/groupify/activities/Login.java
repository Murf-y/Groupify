package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.murfy.groupify.R;
import com.murfy.groupify.api.CrudCallback;
import com.murfy.groupify.api.CrudError;
import com.murfy.groupify.api.UserApi;
import com.murfy.groupify.customElements.DrawableClickListener;
import com.murfy.groupify.databinding.ActivityLoginBinding;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.InputValidator;

import java.util.Objects;

public class Login extends AppCompatActivity {

    boolean showPass = false;
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
                    showPass = !showPass;
                    binding.passwordInput.setTransformationMethod(showPass ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    binding.passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, showPass ? R.drawable.signup_eye_closed : R.drawable.signup_eye,0);
                }
            }
        });

        binding.login.setOnClickListener(view -> {
            String username = binding.usernameInput.getText().toString();
            String password = Objects.requireNonNull(binding.passwordInput.getText()).toString();

            new UserApi(getApplicationContext()).login(username, password, new CrudCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    SharedPreferences shared = getSharedPreferences("groupify", MODE_PRIVATE);
                    shared.edit().putString("current_user", new Gson().toJson(user)).apply();

                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }

                @Override
                public void onError(CrudError error) {
                    binding.errorMessage.setText(error.getMessage());
                    binding.errorMessage.setVisibility(View.VISIBLE);
                    AnimationHelper.getInstance().animateError(binding.errorMessage);
                }
            });if(!InputValidator.isPasswordValid(password)){
                binding.errorMessage.setText("Password must be at least 8 characters");
                binding.errorMessage.setVisibility(View.VISIBLE);
                AnimationHelper.getInstance().animateError(binding.errorMessage);
            }else{

            }

        });

        binding.signupInstead.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Signup.class);
            startActivity(i);
        });
    }
}