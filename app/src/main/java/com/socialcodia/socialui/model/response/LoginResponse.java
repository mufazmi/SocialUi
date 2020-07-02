package com.socialcodia.socialui.model.response;

import com.socialcodia.socialui.model.ModelUser;

public class LoginResponse {
    private Boolean error;
    private String message;
    private ModelUser user;

    public LoginResponse(Boolean error, String message, ModelUser user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

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

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }
}
