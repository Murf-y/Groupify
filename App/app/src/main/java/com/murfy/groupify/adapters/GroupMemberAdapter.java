package com.murfy.groupify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.murfy.groupify.R;
import com.murfy.groupify.models.User;

import java.util.ArrayList;

public class GroupMemberAdapter extends ArrayAdapter<User> {
    private final ArrayList<User> user_list;

    public GroupMemberAdapter(@NonNull Context context, int resource, ArrayList<User> user_list) {
        super(context, resource, user_list);
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.member_list_item, parent, false);
        }
        User user = user_list.get(position);

        ImageView userPhoto = convertView.findViewById(R.id.memberPhoto);

        userPhoto.setImageBitmap(user.getProfilePhoto());

        TextView userName = convertView.findViewById(R.id.memberName);
        userName.setText(user.getUsername());

        TextView userBio = convertView.findViewById(R.id.memberBio);
        userBio.setText(user.getBio());

        return convertView;
    }
}
