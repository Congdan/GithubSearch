package com.example.android.githubsearch.model;

import android.graphics.Bitmap;

import java.util.List;

public class User {

    private String name;
    private String avatar_url;
    private Bitmap avatar;
    private List<Repo> repoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }

    public void setRepoList(List<Repo> repoList) {
        this.repoList = repoList;
    }
}
