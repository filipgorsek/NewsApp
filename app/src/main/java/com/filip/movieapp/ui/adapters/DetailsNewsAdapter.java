package com.filip.movieapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.filip.movieapp.R;
import com.filip.movieapp.model.object.ArticleModel;

import java.util.ArrayList;

public class DetailsNewsAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<ArticleModel> arrayList;

    private ImageView newsImage;
    private TextView newsTitle, newsDescription;

    public DetailsNewsAdapter(Context context, ArrayList<ArticleModel> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_viewpager, container, false);

        newsImage = itemView.findViewById(R.id.detailsNewsImage);
        newsTitle = itemView.findViewById(R.id.title);
        newsDescription = itemView.findViewById(R.id.description);

        ArticleModel item = arrayList.get(position);

        Glide.with(mContext).load(item.getUrlToImage()).into(newsImage);
        newsTitle.setText(item.getTitle());
        newsDescription.setText(item.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}