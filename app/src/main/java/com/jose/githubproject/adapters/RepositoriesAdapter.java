package com.jose.githubproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            holder.repoDescription = (TextView) convertView.findViewById(R.id.repo_description);
            holder.repoLastUpdate = (TextView) convertView.findViewById(R.id.repo_last_update);
            holder.repoLanguage = (TextView) convertView.findViewById(R.id.repo_language);
            holder.repoLanguageLogo = (ImageView) convertView.findViewById(R.id.language_logo);
            holder.forkedIcon = (ImageView) convertView.findViewById(R.id.forked_icon);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.repoName.setText(mUserRepositories.get(position).getRepoName());
        String repoDescription = mUserRepositories.get(position).getRepoDescription();
        if (!repoDescription.equals("null")) {
            holder.repoDescription.setText(repoDescription);
        } else {
            holder.repoDescription.setText("- no description -");
        }

        String updateText = "Updated " + MainActivity.formatDate(mUserRepositories.get(position).getLastUpdate());
        holder.repoLastUpdate.setText(updateText);

        //display image for some languages else, display text
        if (!mUserRepositories.get(position).getRepoLanguage().equals("null"))
            holder.repoLanguageLogo.setImageResource(getLogo(mUserRepositories.get(position).getRepoLanguage()));
        else {
            holder.repoLanguageLogo.setVisibility(View.GONE);
            /*if (mUserRepositories.get(position).getRepoLanguage().equals("null"))
                holder.repoLanguage.setText(mUserRepositories.get(position).getRepoLanguage());
            else
                holder.repoLanguage.setVisibility(View.GONE);*/
        }

        //Displays icon if forked
        if (mUserRepositories.get(position).getIsForked().equals("true"))
            holder.forkedIcon.setVisibility(View.VISIBLE);
        else
            holder.forkedIcon.setVisibility(View.INVISIBLE);

        return convertView;
    }

    private static class ViewHolder {
        TextView repoName;
        TextView repoDescription;
        TextView repoLastUpdate;
        TextView repoLanguage;
        ImageView repoLanguageLogo;
        ImageView forkedIcon;
    }

    private int getLogo(String language) {
        language = language.toLowerCase();
        int id;
        /*
        Java
        Python
        JavaScript
        C
        C++
        Ruby
        PHP
        Go
        C#
        html
        css
        obj-C
        R
        */
        switch (language) {
            case "java":
                id =  R.drawable.java_logo;
                break;
            case "python":
                id = R.drawable.python_logo;
                break;
            case "javascript":
                id = R.drawable.javascript_logo;
                break;
            case "c":
                id = R.drawable.c_logo;
                break;
            case "c++":
                id = R.drawable.cpp_logo;
                break;
            case "ruby":
                id = R.drawable.ruby_logo;
                break;
            case "php":
                id = R.drawable.php_logo;
                break;
            case "c#":
                id = R.drawable.csharp_logo;
                break;
            case "html":
                id = R.drawable.html_logo;
                break;
            case "css":
                id = R.drawable.css3_logo;
                break;
            case "objective-c":
                id = R.drawable.objective_c_logo;
                break;
            case "r":
                id = R.drawable.r_logo;
                break;
            default:
                id = R.drawable.image_missing;
        }

        return id;
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
