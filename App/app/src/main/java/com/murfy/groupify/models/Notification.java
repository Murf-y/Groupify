package com.murfy.groupify.models;

public class Notification {
    private String id;
    private User receiver;
    private Group group_source;
    private String message;
    private String createdAt;

    public Notification(String id, User user, Group group, String msg, String createdAt){
        this.id = id;
        this.receiver = user;
        this.group_source = group;
        this.message = msg;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public User getReceiver() {
        return receiver;
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
