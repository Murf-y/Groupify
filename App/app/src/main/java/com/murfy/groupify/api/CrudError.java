package com.murfy.groupify.api;

public class CrudError {
    private int status;
    private String message;

    public CrudError(int status, String message){
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public int getStatus() {
        return status;
    }
}
