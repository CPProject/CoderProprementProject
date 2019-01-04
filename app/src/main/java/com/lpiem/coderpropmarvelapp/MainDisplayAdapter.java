package com.lpiem.coderpropmarvelapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MainDisplayAdapter extends RecyclerView.Adapter<MainDisplayAdapter.CustomViewHolder> {


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
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
