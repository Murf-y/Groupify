package com.murfy.groupify.models;

public class Notification {
    private String id;
    private Group group_source;
    private String message;
    private String createdAt;

    public Notification(String id, Group group, String msg, String createdAt){
        this.id = id;
        this.group_source = group;
        this.message = msg;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Group getGroup_source() {
        return group_source;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
