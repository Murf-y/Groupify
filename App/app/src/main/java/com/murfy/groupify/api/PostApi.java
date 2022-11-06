package com.murfy.groupify.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.Postable;
import com.murfy.groupify.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostApi {
    private final String post_api_path = "postables.php";

    private final Context context;

    public PostApi(Context context){
        this.context = context;
    }


    public void postPost(String group_id, String user_id, String title, String content, String photo, CrudCallback<Object> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + post_api_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            callback.onSuccess(null);
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
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                params.put("user_id", user_id);
                params.put("group_id", group_id);
                params.put("content", content);
                params.put("post_photo", photo);
                params.put("title", title);

                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }
    public void postMessage(String group_id, String user_id, String content, CrudCallback<Object> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + post_api_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            callback.onSuccess(null);
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
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                params.put("user_id", user_id);
                params.put("group_id", group_id);
                params.put("content", content);
                params.put("post_photo", "");
                params.put("title", "");

                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    public void getPostables(String group_id, CrudCallback<ArrayList<Postable>> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + post_api_path + "?group_id=" + group_id,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<Postable> postables = new ArrayList<>();
                            JSONArray postables_array = res.getJSONArray("data");
                            for(int i = 0; i < postables_array.length(); i++){
                                JSONObject group = postables_array.getJSONObject(i);
                                postables.add(getPostableFromJson(group));
                            }
                            callback.onSuccess(postables);
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
        });

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    private Postable getPostableFromJson(JSONObject res) throws JSONException {
        int id = res.getInt("id");
        String title = res.getString("title");
        String content = res.getString("content");
        String post_photo = res.getString("post_photo");
        String createdAt = res.getString("created_at");
        String user_json_string  = res.getString("sender");
        JSONObject sender_json = new JSONObject(user_json_string);
        User sender=  UserApi.getUserFromJson(sender_json);
        return new Postable(String.valueOf(id), sender, post_photo, title, content, createdAt);
    }
}
