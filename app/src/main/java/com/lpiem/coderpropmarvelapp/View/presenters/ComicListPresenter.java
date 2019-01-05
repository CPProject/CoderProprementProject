package com.lpiem.coderpropmarvelapp.View.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.ComicsManager;
import com.lpiem.coderpropmarvelapp.View.activities.DetailComics;
import com.lpiem.coderpropmarvelapp.View.activities.MainDisplayAdapter;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

import java.util.ArrayList;
import java.util.List;

import static com.lpiem.coderpropmarvelapp.View.activities.MainActivity.SAMPLE_OK;

public class ComicListPresenter {
    private final ComicsManager comicsManager;
    private final Context context;
    private final RecyclerView recyclerView;
    private final List<ComicItem> listComics;
    private final MainDisplayAdapter comicsListAdapter;

    public ComicListPresenter(ComicsManager comicsManager, Context context, RecyclerView rv, MainDisplayAdapter adapter) {
        this.comicsManager = comicsManager;
        this.context = context;
        listComics = new ArrayList<>();
        recyclerView = rv;
        comicsListAdapter = adapter;
    }

    public void updateView(){
        comicsListAdapter.setComicItemList(listComics);
        App.application().getComicsManager().callAsyncTask(SAMPLE_OK, listComics, context, comicsListAdapter, recyclerView);
    }

    public void goToDetailActivity(ComicItem currentComic){
        Intent intent = new Intent(context, DetailComics.class);
        App.application().getComicsManager().setCurrentComics(currentComic);
        context.startActivity(intent);
    }
}
