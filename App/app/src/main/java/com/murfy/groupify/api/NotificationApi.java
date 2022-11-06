package com.murfy.groupify.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationApi {
    private final String notification_api_path = "notification.php";

    private final Context context;

    public NotificationApi(Context context){
        this.context = context;
    }

    public Notification getNotificationFromJson(JSONObject res) throws JSONException {
        int id = res.getInt("id");
        String message = res.getString("message");
        String created_at = res.getString("created_at");
        Group group = GroupApi.getGroupFromJson(res.getJSONObject("group"));
        return new Notification(String.valueOf(id), group, message, created_at);
    }

    public void getUserNotifications(String user_id, CrudCallback<ArrayList<Notification>> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Crud.base_url + notification_api_path+"?user_id="+user_id,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            ArrayList<Notification> notifications = new ArrayList<>();
                            JSONArray notif_array = res.getJSONArray("data");

                            for(int i = 0; i < notif_array.length(); i++){
                                JSONObject notif = notif_array.getJSONObject(i);
                                notifications.add(getNotificationFromJson(notif));
                            }
                            callback.onSuccess(notifications);
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
    public void getNotificationsCount(String user_id, CrudCallback<Integer> callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Crud.base_url + notification_api_path,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response);
                        int status = res.getInt("status");
                        if(status == 200){
                            callback.onSuccess(res.getInt("data"));
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
                return params;
            }
        };

        Crud.getInstance(context).addRequestToQueue(stringRequest);
    }
}
