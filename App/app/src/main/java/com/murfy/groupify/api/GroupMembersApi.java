package com.murfy.groupify.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupMembersApi {
    private final String group_members_api_path = "group_members.php";

    private final Context context;

    public GroupMembersApi(Context context){
        this.context = context;
    }

    public void isMemberInGroup(String user_id, String group_id, CrudCallback<Boolean> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + group_members_api_path+"?user_id="+user_id+"&group_id="+group_id,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            JSONArray members_array = res.getJSONArray("data");
                            callback.onSuccess(members_array.length() > 0);
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

    public void joinGroup(String user_id, String group_id, CrudCallback<Object> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + group_members_api_path,
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
                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    public void leaveGroup(String user_id, String group_id, CrudCallback<Object> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                Crud.base_url + group_members_api_path,
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
                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    public void getGroupMembers(String group_id, CrudCallback<ArrayList<User>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + group_members_api_path+"?group_id="+group_id,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<User> members = new ArrayList<>();
                            JSONArray members_array = res.getJSONArray("data");
                            for(int i = 0; i < members_array.length(); i++){
                                JSONObject user = members_array.getJSONObject(i);
                                members.add(UserApi.getUserFromJson(user));
                            }
                            callback.onSuccess(members);
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
}
