package com.lpiem.coderpropmarvelapp.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.lpiem.coderpropmarvelapp.App;
import com.lpiem.coderpropmarvelapp.View.activities.MainDisplayAdapter;
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

import static com.lpiem.coderpropmarvelapp.View.activities.MainActivity.TAG;

public class ReadFileTask extends AsyncTask<Object, Void, Integer> {

    private Context context;
    private List<ComicItem> comicItems = new ArrayList<>();
    private static final String url = "https://gateway.marvel.com:443/v1/public/comics/item?ts=1524161673&apikey=2fb3c607374cd614f32c819c48e9db0c&hash=4da7ecb9bd380ff6092e35da2a123cc7";
    private MainDisplayAdapter adapter;
    private App app = App.application();
    private static final int REQUEST_READ_STORAGE = 0;

    @Override
    protected Integer doInBackground(Object... params) {
        int result = -1;
        comicItems = (List<ComicItem>) params[1];
        context = (Context) params[2];
        adapter = (MainDisplayAdapter) params[3];
        try {
            final InputStream inputStream = context.getResources().getAssets().open((String) params[0]);

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


        if (result == 1) {
            Log.d(TAG, "onCreate: " + comicItems);
            StringBuilder builder = new StringBuilder();
            for (ComicItem comicItem : comicItems) {
                builder.append(comicItem.getTitle() + "\n");
            }

            app.getComicsManager().setListComics(comicItems);

            adapter.setComicItemList(app.getComicsManager().getListComics());
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Failed to retrieve data", Toast.LENGTH_LONG).show();
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
                    int id = oneComicItem.getInt("id");
                    String title = oneComicItem.getString("title");
                    String description = oneComicItem.getString("description");
                    String diamondCode = oneComicItem.getString("diamondCode");
                    String image = oneComicItem.getString("image");
                    String dateFromJSON = oneComicItem.getString("date");
                    Double price = oneComicItem.getDouble("price");
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
                    String webUrl = setWebUrl(id, title);

                    ComicItem comicItem = new ComicItem(title, description, image, diamondCode, date, creators, pageCount, price, webUrl);
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

    private String setWebUrl(int id, String title) {
        StringBuilder webUrl = new StringBuilder();
        webUrl.append("https://www.marvel.com/comics/issue//");
        webUrl.insert(36, String.valueOf(id));
        webUrl.append(title.replace(" ", "_")
                .replace("#", "")
                .replace("(", "")
                .replace(")", "")
        );
        return webUrl.toString();
    }


}



