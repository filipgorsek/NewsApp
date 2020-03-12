package com.filip.movieapp.api;

public class ApiUtils {

    public static NewsApi getService() {
        return RetrofitClient.getClient().create(NewsApi.class);
    }
}