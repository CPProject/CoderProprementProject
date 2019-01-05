package com.lpiem.coderpropmarvelapp.View.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.ComicsManager;
import com.lpiem.coderpropmarvelapp.R;

public class DetailComics extends AppCompatActivity {

    protected ImageView imageView;
    protected TextView title;
    protected TextView summary;
    protected TextView information;
    protected TextView credits;
    private ComicsManager comicsManager = App.application().getComicsManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcomics);

        imageView = findViewById(R.id.Comicimage);
        title = findViewById(R.id.Comictitle);
        summary = findViewById(R.id.Comicsummary);
        information = findViewById(R.id.Comicinformation);
        credits = findViewById(R.id.Comiccredits);


        //FIXME: to delete, just for test
        Toast.makeText(this, comicsManager.getCurrentComics().toString(), Toast.LENGTH_SHORT).show();


    }
}
