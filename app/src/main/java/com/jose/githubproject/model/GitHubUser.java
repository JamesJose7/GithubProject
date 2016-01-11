package com.jose.githubproject.model;

/**
 * Created by Jose on 1/10/2016.
 */
public class GitHubUser {
    private String mUserName;
    private String mAvatarUrl;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }
}
