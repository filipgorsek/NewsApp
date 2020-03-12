package com.filip.movieapp.model.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.filip.movieapp.model.object.ArticleModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsRepository {

    private NewsDatabase mNewsDatabase;
    public NewsDao newsDao;

    public NewsRepository(Context context) {
        mNewsDatabase = NewsDatabase.getInstance(context);
        newsDao = mNewsDatabase.getNewsDao();
    }

    public void insert(List<ArticleModel> articles){
        for(ArticleModel model : articles){
            mNewsDatabase.getNewsDao().insert(model);
        }
    }

    public List<ArticleModel> getAllArticles() {
        try {
            return new GetArticlesAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetArticlesAsyncTask extends AsyncTask<Void, Void, List<ArticleModel>> {
        @Override
        protected List<ArticleModel> doInBackground(Void... url) {
            return mNewsDatabase.getNewsDao().getArticle();
        }
    }
}