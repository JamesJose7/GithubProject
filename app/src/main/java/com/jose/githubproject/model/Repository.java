package com.jose.githubproject.model;

/**
 * Created by Jose on 1/18/2016.
 */
public class Repository {

    private String mRepoName;
    private String mRepoDescription;
    private String mRepoLanguage;
    private String mLastUpdate;

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
}
