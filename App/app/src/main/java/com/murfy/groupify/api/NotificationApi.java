package com.murfy.groupify.api;

import android.content.Context;

import com.murfy.groupify.models.Notification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationApi {
    private final String notification_api_path = "notification.php";

    private final Context context;

    public NotificationApi(Context context){
        this.context = context;
    }

    public Notification getNotificationFromJson(JSONObject res) throws JSONException {
        return null;
    }

    public void getUserNotifications(String user_id, CrudCallback<ArrayList<Notification>> callback) {
        callback.onSuccess(null);
    }
}
