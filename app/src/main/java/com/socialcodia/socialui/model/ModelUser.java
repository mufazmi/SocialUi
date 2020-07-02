package com.socialcodia.socialui.model;

import android.app.Activity;
import android.content.Context;

import com.socialcodia.socialui.storage.SharedPrefHandler;

public class ModelUser {

    private Integer id,feedsCount,followersCount,followingsCount;
    private Boolean following;
    private String name,username,email,bio,image,token;

    public ModelUser(Integer id, Integer feedsCount, Integer followersCount, Integer followingsCount, Boolean following, String name, String username, String email, String bio, String image, String token) {
        this.id = id;
        this.feedsCount = feedsCount;
        this.followersCount = followersCount;
        this.followingsCount = followingsCount;
        this.following = following;
        this.name = name;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeedsCount() {
        return feedsCount;
    }

    public void setFeedsCount(Integer feedsCount) {
        this.feedsCount = feedsCount;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(Integer followingsCount) {
        this.followingsCount = followingsCount;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
