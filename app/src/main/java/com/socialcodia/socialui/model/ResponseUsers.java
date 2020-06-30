package com.socialcodia.socialui.model;

import java.util.List;

public class ResponseUsers {
    Boolean error;
    String message;
    private List<ModelUser> users;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelUser> getUsers() {
        return users;
    }

    public void setUsers(List<ModelUser> users) {
        this.users = users;
    }
}
