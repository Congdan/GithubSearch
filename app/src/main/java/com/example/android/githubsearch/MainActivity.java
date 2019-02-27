package com.example.android.githubsearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.githubsearch.model.Repository;
import com.example.android.githubsearch.presenter.MainActivityPresenter;
import com.example.android.githubsearch.recyclerView.RecyclerViewAdapter;
import com.example.android.githubsearch.recyclerView.RecyclerViewClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View, RecyclerViewClickListener {

    private MainActivityPresenter mPresenter;

    private EditText mEditText;
    private ImageView mImageView;
    private TextView mTextView;
    private LinearLayout mAvatarContainer;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainActivityPresenter(this);

        mAdapter = new RecyclerViewAdapter(this);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mAvatarContainer = (LinearLayout) findViewById(R.id.avatar_container);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mTextView = (TextView) findViewById(R.id.text_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void onClickSearch(View view) {
        String userInput = mEditText.getText().toString();
        mPresenter.getUser(userInput);
    }

    //Add animation to a given view
    private void animateView(View view) {
        view.setVisibility(View.VISIBLE);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, view.getHeight() / 5, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(translateAnimation);
        animation.addAnimation(alphaAnimation);

        view.startAnimation(animation);
    }

    //Show the detail info of a repo in a dialog, when a repo is clicked
    private void showDialog(String lastUpdated, String stars, String forks) {
        Context context = MainActivity.this;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View repoDetailView = inflater.inflate(R.layout.repo_detail, null);

        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM;

        dialog.setView(repoDetailView);

        ((TextView) repoDetailView.findViewById(R.id.update_display)).setText(lastUpdated);
        ((TextView) repoDetailView.findViewById(R.id.star_display)).setText(stars);
        ((TextView) repoDetailView.findViewById(R.id.fork_display)).setText(forks);

        dialog.show();
    }

    @Override
    public void updateUserInfo(String userName, Bitmap userAvatar) {
        mTextView.setText(userName);
        mImageView.setImageBitmap(userAvatar);
        animateView(mAvatarContainer);
    }

    @Override
    public void updateRepoInfo(List<Repository> repositoryList) {
        mAdapter.updateRepoList(repositoryList);
        animateView(mRecyclerView);
    }

    @Override
    public void showRepoDetail(String lastUpdated, String stars, String forks) {
        showDialog(lastUpdated, stars, forks);
    }

    @Override
    public void recyclerViewClicked(int position) {
        mPresenter.repoClicked(position);
    }
}
