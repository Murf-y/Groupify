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
import com.murfy.groupify.models.Group;
import com.murfy.groupify.models.User;

import java.util.ArrayList;

public class GroupAdapter extends ArrayAdapter<Group> {

    private final ArrayList<Group> group_list;

    public GroupAdapter(@NonNull Context context, int resource, ArrayList<Group> group_list) {
        super(context, resource, group_list);
        this.group_list = group_list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item, parent, false);
        }
        Group group = group_list.get(position);

        ImageView groupImage = convertView.findViewById(R.id.groupImageView);

        groupImage.setImageBitmap(group.getPhoto());


        TextView groupSubject = convertView.findViewById(R.id.groupSubject);
        groupSubject.setText(group.getSubject());

        TextView groupNumberOfMembers = convertView.findViewById(R.id.groupNumberOfMembers);
        groupNumberOfMembers.setText(group.getNumberOfMembers());

        return convertView;
    }
}