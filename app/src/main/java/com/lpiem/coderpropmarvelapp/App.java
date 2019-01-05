package com.lpiem.coderpropmarvelapp;

import android.app.Application;

public class App extends Application {

    private static App application;

    public static App application() {
        return application;
    }

    private ComicsManager comicsManager;

    public ComicsManager getComicsManager() {
        return comicsManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        this.comicsManager = new ComicsManager();
    }

}
