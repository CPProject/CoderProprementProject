package com.lpiem.coderpropmarvelapp.View.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final int REQUEST_WRITE_STORAGE = 0;


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
                if (getPackageManager().checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                    share();
                } else {
                requestStoragePermission();
                }

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


    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    share();
                } else {
                    Toast.makeText(this, "Storage permission is required for sending picture by eMail", Toast.LENGTH_LONG)
                            .show();

                    requestStoragePermission();
                }
            }
        }
    }

    private void share() {
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("image/*");

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
    }
}
