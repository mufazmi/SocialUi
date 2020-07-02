package com.socialcodia.socialui.model.response;

import com.socialcodia.socialui.model.ModelFeed;

public class ResponseFeed {
    private Boolean error;
    private String message;
    private ModelFeed feed;

    public ResponseFeed(Boolean error, String message, ModelFeed feed) {
        this.error = error;
        this.message = message;
        this.feed = feed;
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

    public ModelFeed getFeed() {
        return feed;
    }

    public void setFeed(ModelFeed feed) {
        this.feed = feed;
    }
}
