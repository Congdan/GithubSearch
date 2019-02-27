package com.example.android.githubsearch;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.android.githubsearch.model.Repo;
import com.example.android.githubsearch.model.User;

import java.util.List;

public class MainActivityPresenter {

    private static final String API_PREFIX = "https://api.github.com/users/";

    private View mView;
    private User mUser;

    public MainActivityPresenter(View view) {
        mView = view;
    }

    public void getUser(final String userInput) {
        AndroidNetworking.get(API_PREFIX + userInput)
                .build()
                .getAsObject(User.class, new ParsedRequestListener<User>() {
                    @Override
                    public void onResponse(User user) {
                        mUser = user;
                        getUserAvatar(userInput);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void getUserAvatar(final String userInput) {
        AndroidNetworking.get(mUser.getAvatar_url())
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mUser.setAvatar(bitmap);
                        mView.updateUserInfo(mUser.getName(), mUser.getAvatar());
                        getUserRepo(userInput);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void getUserRepo(String userInput) {
        AndroidNetworking.get(API_PREFIX + userInput + "/repos")
                .build()
                .getAsObjectList(Repo.class, new ParsedRequestListener<List<Repo>>() {
                    @Override
                    public void onResponse(List<Repo> repoList) {
                        mUser.setRepoList(repoList);
                        mView.updateRepoInfo(mUser.getRepoList());
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void repoClicked(int position) {
        Repo repo = mUser.getRepoList().get(position);
        mView.showRepoDetail(repo.getUpdated_at(), String.valueOf(repo.getStargazers_count()), String.valueOf(repo.getForks()));
    }

    public interface View{
        void updateUserInfo(String userName, Bitmap userAvatar);
        void updateRepoInfo(List<Repo> repoList);
        void showRepoDetail(String lastUpdated, String stars, String forks);
    }
}
