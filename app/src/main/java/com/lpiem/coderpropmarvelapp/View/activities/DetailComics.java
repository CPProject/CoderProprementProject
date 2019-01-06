package com.lpiem.coderpropmarvelapp.View.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.ComicsManager;
import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.View.injections.ComicDetailInterface;
import com.lpiem.coderpropmarvelapp.View.presenters.ComicDetailPresenter;
import com.lpiem.coderpropmarvelapp.model.ComicItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class DetailComics extends AppCompatActivity implements ComicDetailInterface {

    protected ImageView imageView;
    protected TextView title;
    protected TextView summary;
    protected TextView information;
    protected TextView credits;
    private ComicsManager comicsManager = App.application().getComicsManager();
    private Toolbar toolbar;
    private ComicDetailPresenter comicDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcomics);

        imageView = findViewById(R.id.Comicimage);
        title = findViewById(R.id.Comictitle);
        summary = findViewById(R.id.Comicsummary);
        information = findViewById(R.id.Comicinformation);
        credits = findViewById(R.id.Comiccredits);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("Detail of Comic");

        comicDetailPresenter = new ComicDetailPresenter(comicsManager, this, this);
        comicDetailPresenter.updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                return true;
            case R.id.action_share:
                Intent myShareIntent = new Intent(Intent.ACTION_SEND);
                myShareIntent.setType("*/*");

                Drawable drawable = imageView.getDrawable();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                File imageFile = saveBitmap(bitmap);

                Uri imageUri = FileProvider.getUriForFile(
                        DetailComics.this,
                        "com.lpiem.coderpropmarvelapp.provider",
                        imageFile);

                myShareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                myShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Look at this Awesome Marvel Comic !");
                myShareIntent.putExtra(Intent.EXTRA_TEXT, comicsManager.getCurrentComicsTitle()
                        + "\n\n" + comicsManager.getCurrentComicsDescription()
                        + "\n\n" + comicsManager.getWebUrl());
                startActivity(myShareIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(ComicItem currentComic) {
        Picasso.get().load(comicsManager.getCurrentComicsImageUrl()).centerInside().fit().placeholder(R.drawable.marvel_logo).into(imageView);
        title.setText(comicsManager.getCurrentComicsTitle());
        summary.setText(comicsManager.getCurrentComicsDescription());
        information.setText(comicsManager.getCurrentComicsInformations());
        credits.setText(comicsManager.getCurrentComicsCreators());
    }

    private File saveBitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, "MarvelPicture.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "MarvelPicture.png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
