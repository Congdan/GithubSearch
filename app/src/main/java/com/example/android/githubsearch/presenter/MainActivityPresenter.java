package com.example.android.githubsearch.presenter;

import android.graphics.Bitmap;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.android.githubsearch.model.Repository;
import com.example.android.githubsearch.model.User;

import java.util.List;

public class MainActivityPresenter {

    private static final String API_PREFIX = "https://api.github.com/users/";

    private View mView;
    private User mUser;

    public MainActivityPresenter(View view) {
        mView = view;
    }

    //Get the user information from the server using the input user id
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

    //Get the user avatar using the url returned from the server
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

    //Get the list of all repos of the user from the server
    private void getUserRepo(String userInput) {
        AndroidNetworking.get(API_PREFIX + userInput + "/repos")
                .build()
                .getAsObjectList(Repository.class, new ParsedRequestListener<List<Repository>>() {
                    @Override
                    public void onResponse(List<Repository> repositoryList) {
                        mUser.setRepositoryList(repositoryList);
                        mView.updateRepoInfo(mUser.getRepositoryList());
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    //Set the repo details to the view when a repo is clicked
    public void repoClicked(int position) {
        Repository repository = mUser.getRepositoryList().get(position);
        mView.showRepoDetail(repository.getUpdated_at(), String.valueOf(repository.getStargazers_count()), String.valueOf(repository.getForks()));
    }

    public interface View{
        //Send the user name and user avatar to the view
        void updateUserInfo(String userName, Bitmap userAvatar);
        //Send a list of repos of the user to the view
        void updateRepoInfo(List<Repository> repositoryList);
        //Send the detail of the clicked repo to the view
        void showRepoDetail(String lastUpdated, String stars, String forks);
    }
}
