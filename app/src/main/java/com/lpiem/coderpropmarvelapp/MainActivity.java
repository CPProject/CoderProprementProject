package com.lpiem.coderpropmarvelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lpiem.coderpropmarvelapp.model.ComicItem;
import com.lpiem.coderpropmarvelapp.model.Creator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private final String TAG = "MainActivity";

    private final List<ComicItem> comicItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int result = parseResult(getResources().getString(R.string.json_data));

        if(result == 1) {
            Log.d(TAG, "onCreate: ComicItems : " + comicItems);
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
