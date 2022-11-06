package com.murfy.groupify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.murfy.groupify.R;
import com.murfy.groupify.models.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    private final ArrayList<Notification> notifications;

    public NotificationAdapter(@NonNull Context context, int resource, ArrayList<Notification> notifications) {
        super(context, resource, notifications);
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_list_item, parent, false);
        }
        Notification notification = notifications.get(position);

        TextView message = convertView.findViewById(R.id.notifMessage);
        message.setText(notification.getMessage());

        return convertView;
    }
}
