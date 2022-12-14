package com.murfy.groupify.models;

import android.graphics.Bitmap;

import com.murfy.groupify.utils.ImageEncoding;

public class Postable {
    private String id;
    private User sender;
    private String photo;
    private String title;
    private String content;
    private String created_at;
    private PostType postType;

    public Postable(String id, User sender, String photo, String title, String content, String create_at){
        this.id = id;
        this.sender = sender;
        this.title = title;
        this.photo = photo;
        this.content = content;
        this.created_at = create_at;
        this.postType = this.title.length() == 0? PostType.Message : PostType.Post;
    }

    public String getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public Bitmap getPhoto() {
        return ImageEncoding.convertToBitmap(photo);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public PostType getPostType() {
        return postType;
    }
}
