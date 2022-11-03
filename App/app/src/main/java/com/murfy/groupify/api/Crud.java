package com.murfy.groupify.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Crud {
    public static Crud instance;
    private final RequestQueue requestQueue;

    public static final String base_url = "http://10.31.210.153/Groupify/Backend/php/";

    public Crud(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public static synchronized Crud getInstance(Context context) {
        if (instance == null) {
            instance = new Crud(context);
        }
        return instance;
    }

    public <T> void addRequestToQueue(Request<T> request){
        requestQueue.add(request);
    }
}
