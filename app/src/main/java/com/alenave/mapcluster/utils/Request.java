package com.alenave.mapcluster.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Request {
    public static String connectionErrorMessage = "Internet connection failed. Please retry or use faster internet connection.";

    public static JSONObject get(String endpoint) {
        HttpURLConnection con = null;
        JSONObject jo = null;
        try {
            URL url = new URL(endpoint);
            if (endpoint.startsWith("https")) {
                con = (HttpsURLConnection) url.openConnection();
            } else {
                con = (HttpURLConnection) url.openConnection();
            }
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(30000);
            int HttpResult = con.getResponseCode();
            String msg = "";
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder resp = new StringBuilder();
                String respline = "";
                while ((respline = in.readLine()) != null) {
                    resp.append(respline);
                }
                msg = resp.toString();
                in.close();
                JSONObject json = null;
                if (msg != null) {
                    json = (JSONObject) new JSONTokener(msg).nextValue();
                }
                if (json != null) {
                    jo = json;
                } else {
                    jo = null;
                }
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            if (jo == null) {
                jo = new JSONObject();
                try {
                    jo.put("success", false);
                    jo.put("message", connectionErrorMessage);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            if (jo == null) {
                jo = new JSONObject();
                try {
                    jo.put("success", false);
                    jo.put("message", connectionErrorMessage);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }
}
