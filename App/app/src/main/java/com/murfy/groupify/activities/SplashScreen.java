package com.murfy.groupify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.murfy.groupify.databinding.ActivitySplashScreenBinding;
import com.murfy.groupify.utils.AnimationHelper;
import com.murfy.groupify.utils.Delayer;

import java.util.concurrent.Callable;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        AnimationHelper.getInstance().fadeIn(binding.logoB, 3000);

        AnimationHelper.getInstance().widenX(binding.circleOne, 4000).widenY(binding.circleOne, 4000);
        AnimationHelper.getInstance().widenX(binding.circleOne2, 4750).widenY(binding.circleOne2, 4750);
        AnimationHelper.getInstance().widenX(binding.circleOne3, 5500).widenY(binding.circleOne3, 5500);
        AnimationHelper.getInstance().widenX(binding.circleOne4, 6000).widenY(binding.circleOne4, 6000);

        Delayer.getInstance().postAfter(() -> {
            AnimationHelper.getInstance().unwidenX(binding.circleOne, 4000).unwidenY(binding.circleOne, 4000);
            AnimationHelper.getInstance().unwidenX(binding.circleOne2, 4750).unwidenY(binding.circleOne2, 4750);
            AnimationHelper.getInstance().unwidenX(binding.circleOne3, 5500).unwidenY(binding.circleOne3, 5500);
            AnimationHelper.getInstance().unwidenX(binding.circleOne4, 6000).unwidenY(binding.circleOne4, 6000);
            return null;
        }, 4000);

        Delayer.getInstance().postAfter(() -> {
            SharedPreferences shared = getApplicationContext().getSharedPreferences("groupify", MODE_PRIVATE);
            if(shared.getBoolean("first_time_open.groupify", true)){
                Intent i = new Intent(getApplicationContext(), OnBoardOne.class);
                startActivity(i);
            }else{
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
            return null;
        }, 10000);
    }
}