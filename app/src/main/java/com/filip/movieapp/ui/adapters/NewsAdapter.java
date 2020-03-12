package com.filip.movieapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.filip.movieapp.R;
import com.filip.movieapp.model.object.ArticleModel;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<ArticleModel> mData;
    private LayoutInflater mInflater;
    private Context context;

    public NewsAdapter(Context context, ArrayList<ArticleModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleModel article = mData.get(position);
        holder.newsItemTextView.setText(article.getTitle());
        Glide.with(context).load(article.getUrlToImage()).into(holder.newsItemImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsItemTextView;
        ImageView newsItemImageView;

        ViewHolder(View itemView) {
            super(itemView);
            newsItemTextView = itemView.findViewById(R.id.newsTitle);
            newsItemImageView = itemView.findViewById(R.id.newsImage);
        }
    }
}