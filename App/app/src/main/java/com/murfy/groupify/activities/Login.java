package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
}