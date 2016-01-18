package com.jose.githubproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose on 1/11/2016.
 */
public class UserRepos {

    public static final String RESULTS_PER_PAGE_100 = "?per_page=100";

    private String mReposUrl;
    private List<Repository> mRepositories;

    public UserRepos() {
        mRepositories = new ArrayList<>();
    }

    public String getReposUrl() {
        return mReposUrl;
    }

    public void setReposUrl(String reposUrl) {
        mReposUrl = reposUrl;
    }

    public List<Repository> getRepositories() {
        return mRepositories;
    }

    public void setRepositories(List<Repository> repositories) {
        mRepositories = repositories;
    }
}
