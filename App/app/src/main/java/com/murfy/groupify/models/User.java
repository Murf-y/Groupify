package com.murfy.groupify.models;

import android.graphics.Bitmap;

import com.murfy.groupify.utils.ImageEncoding;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String username;
    private String email;
    private String bio;
    private String profile_photo;
    private String numberOfGroupsJoined;
    private String numberOfPosts;

    public User(String id, String username, String email, String bio, String profile_photo, String numberOfGroupsJoined, String numberOfPosts){
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.profile_photo = profile_photo;
        this.numberOfGroupsJoined = numberOfGroupsJoined;
        this.numberOfPosts = numberOfPosts;

    }
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getProfilePhoto() {
        return ImageEncoding.convertToBitmap(profile_photo);
    }


    public String getBio() {
        return bio;
    }

    public String getNumberOfGroupsJoined() {
        return numberOfGroupsJoined;
    }

    public String getNumberOfPosts() {
        return numberOfPosts;
    }
}
