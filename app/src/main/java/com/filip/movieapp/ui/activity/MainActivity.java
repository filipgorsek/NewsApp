package com.filip.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.filip.movieapp.R;
import com.filip.movieapp.api.ApiUtils;
import com.filip.movieapp.api.NewsApi;
import com.filip.movieapp.common.RecyclerItemClickListener;
import com.filip.movieapp.model.database.NewsRepository;
import com.filip.movieapp.model.object.Article;
import com.filip.movieapp.model.object.ArticleModel;
import com.filip.movieapp.ui.adapters.NewsAdapter;
import com.filip.movieapp.ui.dialog.WarningDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.filip.movieapp.common.Common.API_KEY;
import static com.filip.movieapp.common.Common.ARRAY_LIST;
import static com.filip.movieapp.common.Common.BBC_KEY;
import static com.filip.movieapp.common.Common.POSITION;
import static com.filip.movieapp.common.Common.TOP_KEY;

public class MainActivity extends AppCompatActivity {

    private NewsApi mService;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private NewsRepository newsRepository;
    private LinearLayout progressCircle;
    private ArrayList<ArticleModel> articles = new ArrayList<>();

    private long currentTimeStamp = (System.currentTimeMillis());
    private static final int FIVE_MINUTES_TO_MILLISECONDS = (5 * 60 * 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("datum", String.valueOf(currentTimeStamp));
        newsRepository = new NewsRepository(getApplicationContext());
        initWidgets();
        progressCircle.setVisibility(View.VISIBLE);
        initService();
        handleData();
    }

    private void initWidgets() {
        progressCircle = findViewById(R.id.llProgressBar);
        recyclerView = findViewById(R.id.newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initService() {
        mService = ApiUtils.getService();
    }

    private void handleData() {
        articles = (ArrayList<ArticleModel>) newsRepository.getAllArticles();
        if (articles.size() == 0) {
            saveTimeStamp();
            getData();
        } else {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            long savedTimeStamp = sp.getLong("ExactTime", 0);
            long differenceTimeStamp = currentTimeStamp - savedTimeStamp;
            if (differenceTimeStamp < FIVE_MINUTES_TO_MILLISECONDS) {
                setData(articles);
            } else {
                saveTimeStamp();
                getData();
            }
        }
    }

    private void saveTimeStamp() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("ExactTime", currentTimeStamp);
        editor.commit();
    }

    private void getData() {
        mService.getArticles(BBC_KEY, TOP_KEY, API_KEY).enqueue(new Callback<Article>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                    newsRepository.insert(response.body().getArticles());
                    articles = new ArrayList<>();
                    articles = (ArrayList<ArticleModel>) newsRepository.getAllArticles();
                    setData(articles);
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable errorMessage) {
                if (call.isCanceled()) {
                    getData();
                } else {
                    WarningDialog warningDialog = new WarningDialog();
                    warningDialog.show(getSupportFragmentManager(), "WD");
                }
            }
        });
    }

    private void setData(ArrayList<ArticleModel> arrayList) {
        newsAdapter = new NewsAdapter(this, arrayList);
        recyclerView.setAdapter(newsAdapter);
        progressCircle.setVisibility(View.GONE);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(MainActivity.this, SingleNewsActivity.class);
                                intent.putExtra(ARRAY_LIST, arrayList);
                                intent.putExtra(POSITION, position);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                            }
                        })
        );
    }
}