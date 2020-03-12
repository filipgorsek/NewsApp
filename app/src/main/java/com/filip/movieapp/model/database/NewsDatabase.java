package com.filip.movieapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.filip.movieapp.model.object.ArticleModel;

@Database(entities = {ArticleModel.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "news_db";
    private static NewsDatabase instance;

    public static NewsDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NewsDatabase.class,
                    DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract NewsDao getNewsDao();
}