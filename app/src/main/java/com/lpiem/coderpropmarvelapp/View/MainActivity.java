package com.lpiem.coderpropmarvelapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lpiem.coderpropmarvelapp.R;
import com.lpiem.coderpropmarvelapp.model.ComicItem;
import com.lpiem.coderpropmarvelapp.model.Creator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private final String TAG = "MainActivity";
    private final String SAMPLE_OK = "sample-ok.json";
    private final String SAMPLE_KO = "sample-ko.json";

    private final List<ComicItem> comicItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ReadFileTask().execute(SAMPLE_OK);

    }

    public class ReadFileTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            int result = -1;
            try {
                final InputStream inputStream = getResources().getAssets().open(params[0]);

                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                int lineNumber = 1;
                boolean continueReading = true;
                while ((line = r.readLine()) != null && continueReading) {
                    response.append(line);
                    if(lineNumber == 2 && line.contains("\"code\": 500,")) {
                        continueReading = false;
                        result = 0;
                        Log.d(TAG, "doInBackground: OUT");
                    }
                    lineNumber++;
                }
                if (result == -1) {
                    result = parseResult(response.toString());
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
//            waiting.setVisibility(View.INVISIBLE);
//            progressBar.setVisibility(View.GONE);
//
            if (result == 1) {
                Log.d(TAG, "onCreate: " + comicItems);
                StringBuilder builder = new StringBuilder();
                for (ComicItem comicItem : comicItems) {
                    builder.append(comicItem.getTitle() + "\n");
                }
                Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_LONG).show();
//                adapter = new MyRecyclerViewAdapter(feedsList);
//                mRecyclerView.setAdapter(adapter);
//
//                mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
//                    @Override
//                    public void onClick(View view, int position) {
//                        ComicItem item = feedsList.get(position);
//                        // passing data to the DetailDisplay Activity
//                        Intent intent = new Intent(MainDisplay.this, DetailDisplay.class);
//                        intent.putExtra("title", item.getTitle());
//                        intent.putExtra("thumbnail", item.getThumbnail());
//                        intent.putExtra("vertical_thumbnail", item.getVerticalThumbnail());
//                        intent.putExtra("date", item.getDate());
//                        intent.putExtra("creators", (Serializable) item.getCreators());
//                        intent.putExtra("diamondCode", item.getDiamondCode());
//                        intent.putExtra("webSite", item.getWebSite());
//                        intent.putExtra("copyright", item.getCopyright());
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onLongClick(View view, int position) {
//                        // Not used here
//                    }
//                }));
//
//                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private int parseResult(String result) {
        int parsingResult = -1;

        try {
            JSONObject jsonResponse = new JSONObject(result);

            int code = jsonResponse.getInt("code");

            if(code == 200) {
                comicItems.clear();

                JSONArray jsonResults = jsonResponse.getJSONArray("results");
                for(int i = 0; i < jsonResults.length(); i++) {

                    JSONObject oneComicItem = jsonResults.getJSONObject(i);
                    String title = oneComicItem.getString("title");
                    String description = oneComicItem.getString("description");
                    String diamondCode = oneComicItem.getString("diamondCode");
                    String image = oneComicItem.getString("image");
                    String dateFromJSON = oneComicItem.getString("date");
                    int pageCount = oneComicItem.getInt("pageCount");
                    JSONArray jsonCreators = oneComicItem.getJSONArray("creators");
                    List<Creator> creators = new ArrayList<>();
                    for (int j = 0; j < jsonCreators.length(); j++) {
                        JSONObject oneCreator = jsonCreators.getJSONObject(j);
                        String creatorName = oneCreator.getString("name");
                        String creatorRole = oneCreator.getString("role");
                        Creator creator = new Creator(creatorName, creatorRole);
                        creators.add(creator);
                    }

                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(dateFromJSON);

                    ComicItem comicItem = new ComicItem(title, description, image, diamondCode, date, creators, pageCount);

                    comicItems.add(comicItem);
                }
                parsingResult = 1;
            } else {
                parsingResult = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsingResult;
    }
}
