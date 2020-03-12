package com.filip.movieapp.api;

import com.filip.movieapp.model.object.Article;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("articles?")
    Call<Article> getArticles(
            @Query("source") String source,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey);
}