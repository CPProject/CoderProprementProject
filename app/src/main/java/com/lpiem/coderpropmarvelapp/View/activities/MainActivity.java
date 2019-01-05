package com.lpiem.coderpropmarvelapp.View.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
        adapter = new MainDisplayAdapter(comicItems);
        recyclerView.setAdapter(adapter);

        // launching the AsynchTask

        comicListPresenter = new ComicListPresenter(App.application().getComicsManager(), this, recyclerView, adapter);
        comicListPresenter.updateView();
        //app.getComicsManager().callAsyncTask(SAMPLE_OK, comicItems, getApplicationContext(), adapter, recyclerView);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ComicItem item = adapter.getComicItemList().get(position);
                app.getComicsManager().setCurrentComics(item);
                Log.d(TAG, "touchListener : " + item.toString());

                comicListPresenter.goToDetailActivity(item);
            }

            @Override
            public void onLongClick(View view, int position) {
                ComicItem item = comicItems.get(position);
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));


    }


}