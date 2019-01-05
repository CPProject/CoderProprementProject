package com.lpiem.coderpropmarvelapp.data;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lpiem.coderpropmarvelapp.View.MainDisplayAdapter;
import com.lpiem.coderpropmarvelapp.View.RecyclerTouchListener;
import com.lpiem.coderpropmarvelapp.model.ComicItem;
import com.lpiem.coderpropmarvelapp.model.Creator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lpiem.coderpropmarvelapp.View.MainActivity.TAG;

public class ReadFileTask extends AsyncTask<Object, Void, Integer> {

    private Context context;
    private MainDisplayAdapter adapter;
    private RecyclerView recyclerView;
    private List<ComicItem> comicItems = new ArrayList<>();
    private static final String url = "https://gateway.marvel.com:443/v1/public/comics/item?ts=1524161673&apikey=2fb3c607374cd614f32c819c48e9db0c&hash=4da7ecb9bd380ff6092e35da2a123cc7";


    public ReadFileTask(Context context, MainDisplayAdapter adapter, RecyclerView recyclerView) {
        this.context = context;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    protected Integer doInBackground(Object... params) {
        int result = -1;
        comicItems = (List<ComicItem>) params[1];
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
            Toast.makeText(context, builder.toString(), Toast.LENGTH_LONG).show();

            adapter = new MainDisplayAdapter(comicItems);
            recyclerView.setAdapter(adapter);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    ComicItem item = comicItems.get(position);
                    Log.d(TAG, "touchListener : " + item.toString());
                    // passing data to the DetailDisplay Activity
//                        Intent intent = new Intent(MainActivity.this, DetailDisplay.class);
//                        intent.putExtra("title", item);
//                        startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {
                    ComicItem item = comicItems.get(position);
                    Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }));

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


                    // grab images online with api key
                    HttpURLConnection urlConnection;
                    try {
                        URL siteUrl = new URL(url.replace("item", oneComicItem.getString("id")));
                        urlConnection = (HttpURLConnection) siteUrl.openConnection();
                        int statusCode = urlConnection.getResponseCode();

                        if (statusCode == 200) {
                            BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = r.readLine()) != null) {
                                response.append(line);
                            }

                            try {
                                JSONObject jsonResponse2 = new JSONObject(response.toString());

                                JSONObject resp = jsonResponse2.getJSONObject("data");
                                JSONArray jsonResults2 = resp.getJSONArray("results");

                                resp = jsonResults2.getJSONObject(0);
                                jsonResults2  = resp.getJSONArray("images");
                                image = jsonResults2.getJSONObject(0).getString("path").concat(".jpg");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    } catch (Exception e) {
                        Log.d(TAG, e.getLocalizedMessage());
                    }

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



