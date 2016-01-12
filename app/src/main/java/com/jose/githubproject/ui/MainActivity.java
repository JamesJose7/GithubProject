package com.jose.githubproject.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jose.githubproject.R;
import com.jose.githubproject.adapters.UserListAdapter;
import com.jose.githubproject.alert.AlertDialogFragment;
import com.jose.githubproject.model.GitHubUser;
import com.jose.githubproject.model.UserRepos;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pkmmte.view.CircularImageView;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_JSON = "UserJson";
    private static final String REPOS_JSON = "ReposJson";

    protected static String apiUrl = "https://api.github.com/";

    private ProgressBar mProgressBar;

    private GitHubUser mGitHubUser;
    private UserRepos mUserRepos;

    ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Image Loader
        configureDefaultImageLoader(this);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader = ImageLoader.getInstance();

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_main);
        mProgressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();

        //Get extras to check if they exist
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String userUrl = intent.getStringExtra(SearchableActivity.USER_URL);
            Toast.makeText(this, "URL: " + userUrl, Toast.LENGTH_LONG).show();

            RelativeLayout searchForUserLayout = (RelativeLayout) findViewById(R.id.search_for_user_layout);
            searchForUserLayout.setVisibility(View.INVISIBLE);

            getContents(userUrl);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getUserContent(String url, String contentFlag) throws JSONException {
        //User Json data
        if (contentFlag.equals(USER_JSON)) {
            mGitHubUser = new GitHubUser();
            mUserRepos = new UserRepos();

            JSONObject userData = new JSONObject(url);
            mGitHubUser.setUserName(userData.getString("login"));
            mGitHubUser.setAvatarUrl(userData.getString("avatar_url"));
            mGitHubUser.setCreationDate(userData.getString("created_at"));

            //Repo Json data
            mUserRepos.setReposUrl(userData.getString("repos_url"));
        }

        if (contentFlag.equals(REPOS_JSON)) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void getContents(String jsonUrl) {

        if (isNetworkAvailable()) {
            //Let the user know data is being loaded
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(jsonUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    //Alert user about error
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        final String jsonData = response.body().string();
                        //Diplay collected data on logcat
                        Log.v("JSON", jsonData);
                        if (response.isSuccessful()) {
                            getUserContent(jsonData, USER_JSON);
                            getUserContent(jsonData, REPOS_JSON);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException | JSONException e) {
                        Log.e("ERROR", "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
            alertUserAboutError();
        }
    }

    private void alertUserAboutError() {
        AlertDialogFragment alertDialog = new AlertDialogFragment();
        alertDialog.show(getFragmentManager(), "error_dialog");
    }

    private void updateDisplay() {
        //Views
        TextView userName = (TextView) findViewById(R.id.selected_user_name);
        TextView creationDate = (TextView) findViewById(R.id.selected_user_creation_date);
        CircularImageView userAvatar = (CircularImageView) findViewById(R.id.selected_user_avatar);

        userName.setText(mGitHubUser.getUserName());

        
        creationDate.setText(mGitHubUser.getCreationDate());
        imageLoader.displayImage(mGitHubUser.getAvatarUrl(), userAvatar, options);
    }

    /*private void parseData(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);

    }*/

    public void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    public static void configureDefaultImageLoader(Context context) {

        ImageLoaderConfiguration defaultConfiguration
                = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(defaultConfiguration);
    }
}
