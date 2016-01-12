package com.jose.githubproject.model;

/**
 * Created by Jose on 1/10/2016.
 */
public class GitHubUser {
    private String mUserName;
    private String mAvatarUrl;
    private String mUserUrl;
    private String mCreationDate;

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

    public String getUserUrl() {
        return mUserUrl;
    }

    public void setUserUrl(String userUrl) {
        mUserUrl = userUrl;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }
}
