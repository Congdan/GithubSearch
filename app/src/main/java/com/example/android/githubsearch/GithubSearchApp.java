package com.example.android.githubsearch;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class GithubSearchApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
