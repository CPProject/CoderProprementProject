package com.lpiem.coderpropmarvelapp.View.presenters;

import android.content.Context;

import com.lpiem.coderpropmarvelapp.ComicsManager;
import com.lpiem.coderpropmarvelapp.View.injections.ComicDetailInterface;

public class ComicDetailPresenter {
    private final ComicsManager comicsManager;
    private final Context context;
    private final ComicDetailInterface comicDetailInterface;

    public ComicDetailPresenter(ComicsManager comicsManager, Context context, ComicDetailInterface comicDetailInterface) {
        this.comicsManager = comicsManager;
        this.context = context;
        this.comicDetailInterface = comicDetailInterface;
    }

    public void updateView(){
        comicDetailInterface.update(comicsManager.getCurrentComics());
    }
}
