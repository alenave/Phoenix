package com.alenave.mapcluster.utils;

public interface Config {
    static final String ENVIRONMENT = "DEVELOPMENT";//DEVELOPMENT|PRODUCTION
    static final String API_URL_DEVELOPMENT = "https://clusters-b7112.firebaseio.com/";
    static final String API_URL_PRODUCTION = "https://clusters-b7112.firebaseio.com/";
    static final String API_URL = ENVIRONMENT.equals("PRODUCTION") ? API_URL_PRODUCTION : API_URL_DEVELOPMENT;
}
