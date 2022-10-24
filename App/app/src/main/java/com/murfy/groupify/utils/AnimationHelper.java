package com.murfy.groupify.utils;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

public class AnimationHelper {

    private static AnimationHelper animationHelper;
    public AnimationHelper(){
    }

    public static AnimationHelper getInstance(){
        if(animationHelper == null) {
            animationHelper = new AnimationHelper();
        }
        return animationHelper;
    }

    public AnimationHelper fadeIn(View v, int duration){
        v.setAlpha(0);
        v.animate().alpha(1).setDuration(duration);

        return  getInstance();
    }

    public AnimationHelper fadeOut(View v, int duration){
        v.setAlpha(1);
        v.animate().alpha(0).setDuration(duration);

        return  getInstance();
    }

    public AnimationHelper scaleUp(View v, int duration) {
        v.setScaleX(0);
        v.setScaleY(0);

        v.animate().scaleY(1).setDuration(duration);
        v.animate().scaleX(1).setDuration(duration);
        return getInstance();
    }
    public AnimationHelper widenX(View v, int duration){
        v.animate().scaleX((float) (v.getScaleX() * 1.3)).setDuration(duration);
        return getInstance();
    }
    public AnimationHelper widenY(View v, int duration){
        v.animate().scaleY((float) (v.getScaleY() * 1.3)).setDuration(duration);
        return getInstance();
    }
    public AnimationHelper unwidenX(View v, int duration){
        v.animate().scaleX((float) (v.getScaleX() * 0.8)).setDuration(duration);
        return getInstance();
    }
    public AnimationHelper unwidenY(View v, int duration){
        v.animate().scaleY((float) (v.getScaleY() * 0.8)).setDuration(duration);
        return getInstance();
    }
}
