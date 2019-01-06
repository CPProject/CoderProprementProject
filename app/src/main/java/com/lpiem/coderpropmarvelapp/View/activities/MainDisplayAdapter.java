package com.lpiem.coderpropmarvelapp.View.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

import java.util.ArrayList;
import java.util.List;

public class MainDisplayAdapter extends RecyclerView.Adapter<MainDisplayAdapter.CustomViewHolder> {

    public static final String PAGE_COUNT = "Page count: ";
    private List<ComicItem> comicItemList = new ArrayList<>();
    private ComicItem comicItem;
    private Context context;

    public MainDisplayAdapter(List<ComicItem> comicItemList, Context context) {
        this.comicItemList = comicItemList;
        this.context = context;
    }

    public void setComicItemList(List<ComicItem> newList) {
        comicItemList = newList;
    }

    public List<ComicItem> getComicItemList() {
        return comicItemList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_comic_item, viewGroup,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        comicItem = comicItemList.get(i);

        Glide.with(context).load(comicItem.getImage()).into(customViewHolder.imageView);

//        Picasso.get().load( "http://i.annihil.us/u/prod/marvel/i/mg/f/03/59e7b08528560.jpg")
//                .error(R.drawable.marvel_logo)
//                .placeholder(R.drawable.marvel_logo)
//                .into(customViewHolder.imageView);


        customViewHolder.title.setText(Html.fromHtml(comicItem.getTitle()));
        customViewHolder.date.setText(Html.fromHtml(String.valueOf(comicItem.getFormatedDate())));
        customViewHolder.pageCount.setText(PAGE_COUNT + Html.fromHtml(String.valueOf(comicItem.getPageCount())));
    }

    @Override
    public int getItemCount() {
        if (comicItemList == null) {
            return 0;
        } else {
        return comicItemList.size();
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageView;
        protected TextView title;
        protected TextView date;
        protected TextView pageCount;


        public CustomViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.imageView);
            this.title =  view.findViewById(R.id.tv_title);
            this.date =  view.findViewById(R.id.tv_date);
            this.pageCount =  view.findViewById(R.id.tv_page_count);
        }


    }
}
