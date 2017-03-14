package com.alenave.mapcluster.activities;

import android.graphics.Color;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import static com.alenave.mapcluster.MainActivity.clusterList;

public class ClusteringActivity extends BaseActivity {
    @Override
    protected void startMap() {
        LatLng yourLocation = new LatLng(12.9533444, 77.6593393);
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 12));
        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    private void readItems() throws JSONException {
        for (int i = 0; i < clusterList.size(); i++) {
            setMarkers(clusterList.get(i).getLat(), clusterList.get(i).getLng(), clusterList.get(i).getCount());
        }
    }

    private void setMarkers(double latitude, double longitude, int clusterCount) {
        getMap().addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title("Cluster")
                .snippet("Containing annotations: " + clusterCount));
        getMap().addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(5000)
                .strokeColor(Color.BLACK)
                .strokeWidth(2)
                .fillColor(0x5500ff00));
    }
}