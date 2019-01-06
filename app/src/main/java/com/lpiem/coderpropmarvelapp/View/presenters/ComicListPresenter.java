package com.lpiem.coderpropmarvelapp.View.presenters;

import android.content.Context;
import android.content.Intent;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.ComicsManager;
import com.lpiem.coderpropmarvelapp.View.activities.DetailComics;
import com.lpiem.coderpropmarvelapp.View.activities.MainDisplayAdapter;
import com.lpiem.coderpropmarvelapp.View.injections.ComicsListInterface;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

import java.util.ArrayList;
import java.util.List;

public class ComicListPresenter {

    public static final String SAMPLE_OK = "sample-ok.json";
    public static final String SAMPLE_KO = "sample-ko.json";
    private final ComicsManager comicsManager;
    private final Context context;
    private final List<ComicItem> listComics;
    private final MainDisplayAdapter comicsListAdapter;
    private ComicsListInterface comicsListInterface;

    public ComicListPresenter(ComicsManager comicsManager, Context context, ComicsListInterface comicsListInterface) {
        this.comicsManager = comicsManager;
        this.context = context;
        this.comicsListInterface = comicsListInterface;
        listComics = new ArrayList<>();
        comicsListAdapter = new MainDisplayAdapter(listComics, context);
    }

    public void updateView(){
        comicsListInterface.update(comicsListAdapter);
        App.application().getComicsManager().callAsyncTask(SAMPLE_OK, listComics, context, comicsListAdapter);
    }

    public void goToDetailActivity(ComicItem currentComic){
        Intent intent = new Intent(context, DetailComics.class);
        App.application().getComicsManager().setCurrentComics(currentComic);
        context.startActivity(intent);
    }
}
