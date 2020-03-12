package com.filip.movieapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.filip.movieapp.model.object.ArticleModel;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void insert(ArticleModel articleModels);

    @Query("SELECT * FROM article")
    List<ArticleModel> getArticle();

}