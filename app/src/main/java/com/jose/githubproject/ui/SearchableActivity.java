package com.jose.githubproject.ui;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jose.githubproject.R;
import com.jose.githubproject.adapters.UserListAdapter;
import com.jose.githubproject.alert.AlertDialogFragment;
import com.jose.githubproject.model.GitHubUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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

public class SearchableActivity extends ListActivity {

    public static final String USER_URL = "UserUrl";

    private ProgressBar mProgressBar;
    private ListActivity mListActivity;

    private List<GitHubUser> mGitHubUSers;

    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        //Views
        mProgressBar = (ProgressBar) findViewById(R.id.list_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);

        //Image Loader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        //Get the query from the intent
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(this, "Query: " + query, Toast.LENGTH_SHORT).show();
            String searchUrlApi = MainActivity.apiUrl + "search/users?q=" + mQuery + "&per_page=40";

            getContents(searchUrlApi);
        }
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
                            parseData(jsonData);
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
        List<String> userNames = new ArrayList<>();
        List<String> userAvatars = new ArrayList<>();

        if(!mGitHubUSers.isEmpty()) {
            for (GitHubUser user : mGitHubUSers) {
                userNames.add(user.getUserName());
                userAvatars.add(user.getAvatarUrl());
            }
        } else {
            TextView noResultsText = (TextView) findViewById(R.id.no_results_text);
            noResultsText.setText("No results found for: " + mQuery);
        }

        UserListAdapter userListAdapter = new UserListAdapter(this, userNames, userAvatars);
        setListAdapter(userListAdapter);

    }

    private void parseData(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray itemsFound = jsonObject.getJSONArray("items");

        mGitHubUSers = new ArrayList<>();

        for (int i = 0; i  < itemsFound.length(); i++) {
            GitHubUser gitHubUser = new GitHubUser();

            JSONObject item = itemsFound.getJSONObject(i);
            gitHubUser.setUserName(item.getString("login"));
            gitHubUser.setAvatarUrl(item.getString("avatar_url"));
            gitHubUser.setUserUrl(item.getString("url"));

            mGitHubUSers.add(gitHubUser);
        }
    }

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        String userUrl = mGitHubUSers.get(position).getUserUrl();
        //Toast.makeText(SearchableActivity.this, "URL: " + userUrl, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_URL, userUrl);
        startActivity(intent);

    }
}
