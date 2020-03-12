package com.filip.movieapp.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.filip.movieapp.R;
import com.filip.movieapp.model.object.ArticleModel;
import com.filip.movieapp.ui.adapters.DetailsNewsAdapter;

import java.util.ArrayList;

import static com.filip.movieapp.common.Common.ARRAY_LIST;
import static com.filip.movieapp.common.Common.POSITION;

public class SingleNewsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int position;
    private ArrayList<ArticleModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);
        arrayList = (ArrayList<ArticleModel>) getIntent().getSerializableExtra(ARRAY_LIST);
        position = (int) getIntent().getSerializableExtra(POSITION);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();
    }

    private void initWidgets() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new DetailsNewsAdapter(this, arrayList));
        viewPager.setCurrentItem(position);
        getSupportActionBar().setTitle(arrayList.get(position).getTitle());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        viewPager.setNextFocusRightId(position);
                        getSupportActionBar().setTitle(arrayList.get(position).getTitle());
                    }
                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int pos) {
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}