package com.example.android.githubsearch.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.githubsearch.R;
import com.example.android.githubsearch.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Repo> mRepoList;
    private static  RecyclerViewClickListener mListener;

    public RecyclerViewAdapter(RecyclerViewClickListener listener) {
        mRepoList = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View repoInfoView = inflater.inflate(R.layout.repo_info, parent, false);
        int height = ((View) parent).getHeight() / 4;
        repoInfoView.getLayoutParams().height = height;

        final ViewHolder viewHolder = new ViewHolder(repoInfoView);

        repoInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.recyclerViewClicked(viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Repo repo = mRepoList.get(position);

        ((TextView) holder.repoName).setText(repo.getName());
        ((TextView) holder.repoDescription).setText(repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView repoName;
        public TextView repoDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            repoName = (TextView) itemView.findViewById(R.id.repo_name);
            repoDescription = (TextView) itemView.findViewById(R.id.repo_description);
        }
    }

    public void updateRepoList(List<Repo> repoList) {
        mRepoList = new ArrayList<>(repoList);
        this.notifyDataSetChanged();
    }
}
