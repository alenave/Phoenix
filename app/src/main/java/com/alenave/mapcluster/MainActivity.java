package com.alenave.mapcluster;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alenave.mapcluster.activities.ClusteringActivity;
import com.alenave.mapcluster.model.Cluster;
import com.alenave.mapcluster.utils.Config;
import com.alenave.mapcluster.utils.InternetConnection;
import com.alenave.mapcluster.utils.JsonParser;
import com.alenave.mapcluster.utils.Request;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout firstLoading;
    private Button cluster;
    private Button retry;
    public static List<Cluster> clusterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if (!InternetConnection.isInternetConnected(getApplicationContext())) {
            retry.setVisibility(View.VISIBLE);
            cluster.setVisibility(View.GONE);
        } else {
            readClusters();
        }
    }

    public void initViews() {
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(this);
        cluster = (Button) findViewById(R.id.cluster);
        cluster.setOnClickListener(this);
        firstLoading = (RelativeLayout) findViewById(R.id.first_loading_layout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.cluster:
                startActivity(new Intent(this, ClusteringActivity.class));
                break;

        }
    }

    public void readClusters() {

        new AsyncTask<String, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                firstLoading.setVisibility(View.VISIBLE);
                cluster.setVisibility(View.GONE);
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                try {
                    JSONObject feedObject = new Request().get(Config.API_URL + "list/cluster.json");
                    return feedObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject feedObject) {
                super.onPostExecute(feedObject);
                try {
                    if (feedObject != null) {
                        clusterList.clear();
                        JsonParser jsonParser = new JsonParser();
                        clusterList.addAll(jsonParser.parse(feedObject));
                        firstLoading.setVisibility(View.GONE);
                        cluster.setVisibility(View.VISIBLE);
                    } else {
                        retry.setVisibility(View.VISIBLE);
                        firstLoading.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Something went wrong, Please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e)

                {
                    e.printStackTrace();
                }
            }


        }.execute(null, null, null);
    }
}
