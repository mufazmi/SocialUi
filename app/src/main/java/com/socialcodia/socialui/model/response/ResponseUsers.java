package com.socialcodia.socialui.model.response;

import com.socialcodia.socialui.model.ModelUser;

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
