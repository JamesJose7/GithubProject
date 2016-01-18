package com.jose.githubproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jose.githubproject.R;
import com.jose.githubproject.model.Repository;
import com.jose.githubproject.ui.MainActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Jose on 1/18/2016.
 */
public class RepositoriesAdapter extends BaseAdapter {
    private Context mContext;
    private List<Repository> mUserRepositories;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    public RepositoriesAdapter(Context context, List<Repository> userRepositories) {
        mContext = context;
        mUserRepositories = userRepositories;

        MainActivity.configureDefaultImageLoader(context);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.repository_template, null);
            holder = new ViewHolder();

            holder.repoName = (TextView) convertView.findViewById(R.id.repo_name);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.repoName.setText(mUserRepositories.get(position).getRepoName());

        return convertView;
    }

    private static class ViewHolder {
        TextView repoName;
    }


    @Override
    public int getCount() {
        return mUserRepositories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
