package com.jose.githubproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jose.githubproject.R;
import com.jose.githubproject.model.LanguageLogo;
import com.jose.githubproject.model.Repository;
import com.jose.githubproject.ui.MainActivity;
import com.jose.githubproject.ui.RepoActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Jose on 1/18/2016.
 */
public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder> {

    private Repository[] mRepositories;
    private Context mContext;

    public RepositoriesAdapter(Repository[] repositories) {
        mRepositories = repositories;
    }

    @Override
    public RepositoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_template, parent, false);
        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoriesViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository selectedRepo = mRepositories[position];
                Intent intent = new Intent(mContext, RepoActivity.class);
                intent.putExtra(MainActivity.SELECTED_REPO, selectedRepo.getRepoUrl());
                mContext
                        .startActivity(intent);
            }
        });
        holder.bindRepository(mRepositories[position]);
    }

    @Override
    public int getItemCount() {
        return mRepositories.length;
    }

    public class RepositoriesViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView repoName;
        public TextView repoDescription;
        public TextView repoLastUpdate;
        public TextView repoLanguage;
        public ImageView repoLanguageLogo;
        public ImageView forkedIcon;

        public RepositoriesViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view_layout);
            repoName = (TextView) itemView.findViewById(R.id.repo_name);
            repoDescription = (TextView) itemView.findViewById(R.id.repo_description);
            repoLastUpdate = (TextView) itemView.findViewById(R.id.repo_last_update);
            repoLanguage = (TextView) itemView.findViewById(R.id.repo_language);
            repoLanguageLogo = (ImageView) itemView.findViewById(R.id.language_logo);
            forkedIcon = (ImageView) itemView.findViewById(R.id.forked_icon);
        }

        public void bindRepository(Repository repository) {
            repoName.setText(repository.getRepoName());
            String description = repository.getRepoDescription();
            if (!repository.getRepoDescription().equals("null")) {
                repoDescription.setText(description);
            } else {
                repoDescription.setText("- no description -");
            }

            String updateText = "Updated " + MainActivity.formatDate(repository.getLastUpdate());
            repoLastUpdate.setText(updateText);

            //display image for some languages else, display text
            if (!repository.getRepoLanguage().equals("null"))
                repoLanguageLogo.setImageResource(findLogoIdByName(repository.getRepoLanguage()));
            else {
                //repoLanguageLogo.setVisibility(View.GONE);

                //Displays icon if forked
                if (repository.getIsForked().equals("true"))
                    forkedIcon.setVisibility(View.VISIBLE);
                else
                    forkedIcon.setVisibility(View.INVISIBLE);
            }
        }
    }


    public int findLogoIdByName(String name) {
        int id = LanguageLogo.NONE.getId();
        LanguageLogo[] languageLogos = LanguageLogo.values();
        for (LanguageLogo logo : languageLogos) {
            if (logo.getName().equals(name.toLowerCase())) {
                id = logo.getId();
            }
        }

        return id;
    }
}
