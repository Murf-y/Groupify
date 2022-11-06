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
import com.murfy.groupify.models.PostType;
import com.murfy.groupify.models.Postable;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Postable> {

    ArrayList<Postable> posts;
    public PostAdapter(Context context, ArrayList<Postable> posts) {
        super(context, 0, posts);
        this.posts = posts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Postable post = posts.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(post.getPostType() == PostType.Message ? R.layout.postable_message_list_item : R.layout.postable_post_list_item, parent, false);
        }

        ImageView memberPhoto = convertView.findViewById(R.id.memberPhoto);
        memberPhoto.setImageBitmap(post.getSender().getProfilePhoto());


        TextView memberName = convertView.findViewById(R.id.memberName);
        memberName.setText(post.getSender().getUsername());

        TextView sentAt = convertView.findViewById(R.id.sentAt);
        sentAt.setText(post.getCreated_at().split(" ")[1]);

        TextView messageContent = convertView.findViewById(R.id.messageContent);
        messageContent.setText(post.getContent());

        if(post.getPostType() == PostType.Post){
            TextView messageTitle = convertView.findViewById(R.id.postTitle);
            messageTitle.setText(post.getTitle());

            ImageView postPhoto = convertView.findViewById(R.id.postImage);
            postPhoto.setImageBitmap(post.getPhoto());
        }

        return convertView;
    }
}
