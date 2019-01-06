package com.lpiem.coderpropmarvelapp.View.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.ComicsManager;
import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.View.injections.ComicsListInterface;
import com.lpiem.coderpropmarvelapp.View.presenters.ComicListPresenter;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

public class MainActivity extends AppCompatActivity implements ComicsListInterface {

    public static final String TAG = "MainActivity";
    protected RecyclerView recyclerView;
    private App app = App.application();
    private ComicsManager comicsManager;
    private ComicListPresenter comicListPresenter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Using ToolBar");

        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        comicsManager = app.getComicsManager();

        comicListPresenter = new ComicListPresenter(comicsManager, this, this);
        comicListPresenter.updateView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(final MainDisplayAdapter mainDisplayAdapter) {
        recyclerView.setAdapter(mainDisplayAdapter);
        mainDisplayAdapter.setComicItemList(comicsManager.getListComics());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ComicItem item = mainDisplayAdapter.getComicItemList().get(position);
                comicsManager.setCurrentComics(item);
                Log.d(TAG, "touchListener : " + item.toString());

                comicListPresenter.goToDetailActivity(item);
            }

            @Override
            public void onLongClick(View view, int position) {
                ComicItem item = mainDisplayAdapter.getComicItemList().get(position);
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));

    }



}
