package com.jose.githubproject.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jose.githubproject.R;
import com.jose.githubproject.ui.MainActivity;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Jose on 1/10/2016.
 */
public class UserListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mUserNames;
    private List<String> mUserAvatar;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    public UserListAdapter(Context context, List<String> userNames, List<String> userAvatar) {
        mContext = context;
        mUserNames = userNames;
        mUserAvatar = userAvatar;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_list_template, null);
            holder = new ViewHolder();

            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.userAvatar = (CircularImageView) convertView.findViewById(R.id.user_avatar);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.userAvatar.setMaxWidth(100);
        holder.userAvatar.setMaxHeight(100);

        //holder.userAvatar.setImageResource(R.drawable.image_missing);
        imageLoader.displayImage(mUserAvatar.get(position), holder.userAvatar, options);
        holder.userName.setText(mUserNames.get(position));

        return convertView;
    }

    private static class ViewHolder {
        TextView userName;
        CircularImageView userAvatar;
    }


    @Override
    public int getCount() {
        return mUserNames.size();
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
