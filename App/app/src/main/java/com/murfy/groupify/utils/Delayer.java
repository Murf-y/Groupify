package com.murfy.groupify.utils;

import android.os.Handler;

import java.util.concurrent.Callable;

public class Delayer {
    private static Delayer delayer;
    public Delayer(){
    }
    public static Delayer getInstance(){
        if(delayer == null) {
            delayer = new Delayer();
        }
        return delayer;
    }

    public Delayer postAfter(Callable<Void> func, int delay){
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    func.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delay);

        return getInstance();
    }

}
