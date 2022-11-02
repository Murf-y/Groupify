package com.murfy.groupify.api;

public interface CrudCallback<T> {
    public void onSuccess(T t);
    public void onError(CrudError error);
}
