package com.lpiem.coderpropmarvelapp.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

import java.util.List;

public class MainDisplayAdapter extends RecyclerView.Adapter<MainDisplayAdapter.CustomViewHolder> {

    private List<ComicItem> comicItemList;
    private ComicItem comicItem;

    public MainDisplayAdapter(List<ComicItem> comicItemList) {
        this.comicItemList = comicItemList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_comic_item, viewGroup,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
