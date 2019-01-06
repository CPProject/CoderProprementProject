package com.lpiem.coderpropmarvelapp.View.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    private Toolbar toolbar;
    private ShareActionProvider mShareActionProvider;
    ActionMode.Callback actionModeCallBack;

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

        //FIXME: to delete, just for test
        Toast.makeText(this, comicsManager.getCurrentComics().toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de share_menu à l'ActionBar
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                return true;
            case R.id.action_share:
                //mShareActionProvider = (ShareActionProvider) item.getActionProvider();
                Intent myShareIntent = new Intent(Intent.ACTION_SEND);
                myShareIntent.setType("text/*");
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                /* ci-dessous, j'ai mis un peu n'imp pour que ça mette pas de rouge... mais je ne crois pas qu'on récupère l'url de l'image comme cela loul */
                myShareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageView.toString()));
                myShareIntent.putExtra(Intent.EXTRA_STREAM, title.toString());
                startActivity(myShareIntent);
                //mShareActionProvider.setShareIntent(myShareIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
