package com.socialcodia.socialui.model;

public class ModelUser {

    private Integer id;
    private Boolean following;
    private String name,username,email,bio,image,token,feedsCount,followersCount,followingsCount;

    public ModelUser(Integer id, Boolean following, String name, String username, String email, String bio, String image, String token, String feedsCount, String followersCount, String followingsCount) {
        this.id = id;
        this.following = following;
        this.name = name;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.token = token;
        this.feedsCount = feedsCount;
        this.followersCount = followersCount;
        this.followingsCount = followingsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFeedsCount() {
        return feedsCount;
    }

    public void setFeedsCount(String feedsCount) {
        this.feedsCount = feedsCount;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(String followersCount) {
        this.followersCount = followersCount;
    }

    public String getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(String followingsCount) {
        this.followingsCount = followingsCount;
    }
}
