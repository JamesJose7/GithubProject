package com.jose.githubproject.ui;

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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jose.githubproject.R;
import com.jose.githubproject.adapters.UserListAdapter;
import com.jose.githubproject.alert.AlertDialogFragment;
import com.jose.githubproject.model.GitHubUser;
import com.jose.githubproject.model.Repository;
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

public class RepoActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String repoUrl = intent.getStringExtra(MainActivity.SELECTED_REPO);

        //Views assignment
        mProgressBar = (ProgressBar) findViewById(R.id.repo_progress_bar);

        getContents(repoUrl);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.format("Selected repository: %s", repoUrl);
                Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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


    }

    private void parseData(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);

        Repository repository = new Repository();

        repository.setRepoName(jsonObject.getString("name"));
        repository.setRepoDescription(jsonObject.getString("description"));
        repository.setIsForked(jsonObject.getString("fork"));
        repository.setLastUpdate(jsonObject.getString("updated_at"));
        repository.setRepoLanguage(jsonObject.getString("language"));

        //Owner data
        JSONObject ownerJson = jsonObject.getJSONObject("owner");
        repository.setOwner(ownerJson.getString("login"));

        //Parent data
        if (repository.getIsForked().equals("true")) {
            JSONObject parentJson = jsonObject.getJSONObject("parent");
            JSONObject parentOwnerJson = parentJson.getJSONObject("owner");
            repository.setParentOwner(parentOwnerJson.getString("login"));
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

}
