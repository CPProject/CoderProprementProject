package com.lpiem.coderpropmarvelapp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.ReadFileTask;
import com.lpiem.coderpropmarvelapp.model.ComicItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";
    private final String SAMPLE_OK = "sample-ok.json";
    private final String SAMPLE_KO = "sample-ko.json";
    protected MainDisplayAdapter adapter;
    protected RecyclerView recyclerView;

    private final List<ComicItem> comicItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // launching the AsynchTask
        new ReadFileTask(this, adapter, recyclerView).execute(SAMPLE_OK, comicItems);


    }


}
