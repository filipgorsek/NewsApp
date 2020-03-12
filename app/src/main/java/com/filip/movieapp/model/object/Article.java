package com.filip.movieapp.model.object;

import java.io.Serializable;
import java.util.ArrayList;

public class Article implements Serializable {

    private ArrayList<ArticleModel> articles = new ArrayList<>();

    public ArrayList<ArticleModel> getArticles() {
        return articles;
    }
}