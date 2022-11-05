package com.murfy.groupify.api;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.murfy.groupify.R;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;
import com.murfy.groupify.utils.ImageEncoding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupApi {
    private final String group_api_path = "group.php";

    private final Context context;

    public GroupApi(Context context){
        this.context = context;
    }

    public Group getGroupFromJson(JSONObject res) throws JSONException {
        int id = res.getInt("id");
        String subject = res.getString("subject");
        String description = res.getString("description");
        String numberOfMembers = res.getString("number_of_members");
        String photo = res.getString("group_photo");
        String createdAt = res.getString("created_at");
        String ownerId = res.getString("owner_id");
        return new Group(
                String.valueOf(id),
                subject,
                description,
                photo,
                ownerId,
                createdAt,
                numberOfMembers
        );
    }

    public void getAllGroups(final CrudCallback<ArrayList<Group>> callback){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + group_api_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<Group> groups = new ArrayList<>();
                            JSONArray groups_array = res.getJSONArray("data");
                            for(int i = 0; i < groups_array.length(); i++){
                                JSONObject group = groups_array.getJSONObject(i);
                                groups.add(getGroupFromJson(group));
                            }
                            callback.onSuccess(groups);
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

    public void getMyGroups(String userId, CrudCallback<ArrayList<Group>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + group_api_path + "?user_id=" + userId,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<Group> groups = new ArrayList<>();
                            JSONArray groups_array = res.getJSONArray("data");
                            for(int i = 0; i < groups_array.length(); i++){
                                JSONObject group = groups_array.getJSONObject(i);
                                groups.add(getGroupFromJson(group));
                            }
                            callback.onSuccess(groups);
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

    public void getGroupsCreatedByMe(String ownerId, CrudCallback<ArrayList<Group>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + group_api_path + "?owner_id=" + ownerId,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<Group> groups = new ArrayList<>();
                            JSONArray groups_array = res.getJSONArray("data");
                            for(int i = 0; i < groups_array.length(); i++){
                                JSONObject group = groups_array.getJSONObject(i);
                                groups.add(getGroupFromJson(group));
                            }
                            callback.onSuccess(groups);
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
    public void createGroup(String owner_id, String subject, String description, String group_photo, CrudCallback<Group> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + group_api_path,
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
                params.put("user_id", owner_id);
                params.put("subject", subject);
                params.put("description", description);
                params.put("group_photo", group_photo);

                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }

    public void searchForGroups(String search_term, CrudCallback<ArrayList<Group>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + group_api_path + "?search_term=" + search_term,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<Group> groups = new ArrayList<>();
                            JSONArray groups_array = res.getJSONArray("data");
                            for(int i = 0; i < groups_array.length(); i++){
                                JSONObject group = groups_array.getJSONObject(i);
                                groups.add(getGroupFromJson(group));
                            }
                            callback.onSuccess(groups);
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
