package com.murfy.groupify.models;

import android.graphics.Bitmap;

import com.murfy.groupify.utils.ImageEncoding;

import java.io.Serializable;

public class Group implements Serializable {
    private String id;
    private String subject;
    private String description;
    private String photo;
    private String owner_id;
    private String created_at;
    private String number_of_members;

    public Group(String id, String subject, String description, String photo, String owner_id, String created_at, String number_of_members){
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.photo = photo;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.number_of_members = number_of_members;
    }
    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getPhoto() {
        return ImageEncoding.convertToBitmap(photo);
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getNumberOfMembers() {
        return number_of_members;
    }
}
