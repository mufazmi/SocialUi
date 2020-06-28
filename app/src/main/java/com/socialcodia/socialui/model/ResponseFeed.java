package com.socialcodia.socialui.model;

import java.util.List;

public class ResponseFeed {
    String message;
    Boolean error;
    private List<ModelFeed> feeds;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<ModelFeed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<ModelFeed> feeds) {
        this.feeds = feeds;
    }
}
