package com.lpiem.coderpropmarvelapp.View.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.View.presenters.ComicListPresenter;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";
    public static final String SAMPLE_OK = "sample-ok.json";
    public static final String SAMPLE_KO = "sample-ko.json";
    protected MainDisplayAdapter adapter;
    protected RecyclerView recyclerView;
    private App app = App.application();
    private ComicListPresenter comicListPresenter;

    private final List<ComicItem> comicItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // launching the AsynchTask

        comicListPresenter = new ComicListPresenter(App.application().getComicsManager(), this, recyclerView);
        comicListPresenter.updateView();
        //app.getComicsManager().callAsyncTask(SAMPLE_OK, comicItems, getApplicationContext(), adapter, recyclerView);


    }


}
