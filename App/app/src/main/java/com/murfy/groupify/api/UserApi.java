package com.murfy.groupify.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.murfy.groupify.R;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.ImageEncoding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserApi {
    private final String login_path = "login.php";
    private final String signup_path = "signup.php";
    private final String user_api_path = "user.php";
    
    private final Context context;

    public UserApi(Context context){
        this.context = context;
    }

    public static User getUserFromJson(JSONObject res) throws JSONException{
        int id = res.getInt("id");
        String stored_username = res.getString("username");
        String stored_email = res.getString("email");
        String bio = res.getString("bio");
        String profile_photo = res.getString("profile_photo");
        String numberOfGroupsJoined = res.getString("num_groups");
        String numberOfPosts = res.getString("num_posts");
        return new User(
                String.valueOf(id),
                stored_username,
                stored_email,
                bio,
                profile_photo,
                numberOfGroupsJoined,
                numberOfPosts
        );
    }
    public void signUp(String username, String email, String password, final CrudCallback<User> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + signup_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            User user = getUserFromJson(res);
                            callback.onSuccess(user);
                        }else{
                            CrudError error = new CrudError(status, res.getString("message"));
                            callback.onError(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CrudError error = new CrudError(400, "Unknown Error occurred, try again later");
                        callback.onError(error);
                    }

                }, error -> {
                    Log.i("ERROR", error.toString());
                    CrudError err = new CrudError(500, error.getMessage());
                    callback.onError(err);
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                Bitmap default_photo = ImageEncoding.getBitmapFromXml(context.getDrawable(R.drawable.profile_default));
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("profile_photo", ImageEncoding.convertToBase64(default_photo));
                return params;
            }
        };
        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    public void login(String username, String password, final CrudCallback<User> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + login_path,
                response -> {
                Log.i("DEBUG", response);
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            User user = getUserFromJson(res);
                            callback.onSuccess(user);
                        }else{
                            CrudError error = new CrudError(status, res.getString("message"));
                            callback.onError(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CrudError error = new CrudError(400, "Unknown Error occurred, try again later");
                        callback.onError(error);
                    }

                }, error -> {
            Log.i("ERROR", error.toString());
            CrudError err = new CrudError(500, error.getMessage());
            callback.onError(err);
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    public void updateUser(String id, String username, String bio, String profile_photo , final CrudCallback<User> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + user_api_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            User user = getUserFromJson(res.getJSONObject("data"));
                            callback.onSuccess(user);
                        }else{
                            CrudError error = new CrudError(status, res.getString("message"));
                            callback.onError(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        CrudError error = new CrudError(400, "Unknown Error occurred, try again later");
                        callback.onError(error);
                    }

                }, error -> {
            Log.i("ERROR", error.toString());
            CrudError err = new CrudError(500, error.getMessage());
            callback.onError(err);
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("username", username);
                params.put("bio", bio);
                params.put("profile_photo", profile_photo);
                return params;
            }
        };
        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

}
