package com.alenave.mapcluster.utils;

import com.alenave.mapcluster.model.Cluster;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonParser {
    Cluster currItem = null;

    public List<Cluster> parse(JSONObject jo) throws IOException, JSONException {

        List<Cluster> clusterList = new ArrayList<>();
        if (jo != null) {
            try {
                Iterator<?> keys = jo.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    JSONObject jsonObject = (JSONObject) jo.get(key);
                    if (jo.get(key) instanceof JSONObject) {
                        currItem = new Cluster();
                        if (jsonObject.has("lat")) {
                            currItem.setLat(Double.valueOf(jsonObject.getString("lat")));
                        }
                        if (jsonObject.has("lng")) {
                            currItem.setLng(Double.valueOf(jsonObject.getString("lng")));
                        }
                        if (jsonObject.has("count")) {
                            currItem.setCount(Integer.valueOf(jsonObject.getString("count")));
                        }
                        clusterList.add(currItem);
                    }
                }
                return clusterList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}