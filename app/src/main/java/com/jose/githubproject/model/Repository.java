package com.jose.githubproject.model;

/**
 * Created by Jose on 1/18/2016.
 */
public class Repository {

    private String mRepoName;
    private String mRepoDescription;
    private String mRepoLanguage;
    private String mLastUpdate;
    private String mIsForked;
    private String mRepoUrl;
    private String mCreation;
    private String mOwner;
    private String mParentOwner;

    public String getRepoName() {
        return mRepoName;
    }

    public void setRepoName(String repoName) {
        mRepoName = repoName;
    }

    public String getRepoDescription() {
        return mRepoDescription;
    }

    public void setRepoDescription(String repoDescription) {
        mRepoDescription = repoDescription;
    }

    public String getRepoLanguage() {
        return mRepoLanguage;
    }

    public void setRepoLanguage(String repoLanguage) {
        mRepoLanguage = repoLanguage;
    }

    public String getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        mLastUpdate = lastUpdate;
    }

    public String getIsForked() {
        return mIsForked;
    }

    public void setIsForked(String isForked) {
        mIsForked = isForked;
    }

    public String getRepoUrl() {
        return mRepoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        mRepoUrl = repoUrl;
    }

    public String getCreation() {
        return mCreation;
    }

    public void setCreation(String creation) {
        mCreation = creation;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getParentOwner() {
        return mParentOwner;
    }

    public void setParentOwner(String parentOwner) {
        mParentOwner = parentOwner;
    }
}
