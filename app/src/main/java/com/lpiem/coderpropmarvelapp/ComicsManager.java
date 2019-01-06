package com.lpiem.coderpropmarvelapp;

import android.content.Context;

import com.lpiem.coderpropmarvelapp.View.activities.MainDisplayAdapter;
import com.lpiem.coderpropmarvelapp.data.ReadFileTask;
import com.lpiem.coderpropmarvelapp.model.ComicItem;
import com.lpiem.coderpropmarvelapp.model.Creator;

import java.util.List;

public class ComicsManager {

    private ComicItem currentComics;
    private List<ComicItem> listComics;

    public ComicsManager() {
    }

    public void setCurrentComics(ComicItem currentComics) {
        this.currentComics = currentComics;
    }

    public void setListComics(List<ComicItem> listComics) {
        this.listComics = listComics;
    }

    public String getCurrentComicsCreators(){
        StringBuilder textToReturn = new StringBuilder();
        for(int position =0; position<currentComics.getCreators().size(); position++) {
            if (position > 0) {
                textToReturn.append("\n");
            }
            textToReturn
                    .append(currentComics.getCreators().get(position).getName())
                    .append(" - ").append(currentComics.getCreators().get(position).getRole());
        }
        return textToReturn.toString();
    }

    public String getCurrentComicsInformations(){
        StringBuilder textToReturn = new StringBuilder();
        textToReturn.append(currentComics.getFormatedDate()).append("\nPrice: ");
        textToReturn
                .append(currentComics.getFormattedPrice())
                .append(" , Pages: ")
                .append(currentComics.getPageCount())
                .append(" - DiamondCode: ")
                .append(currentComics.getDiamondCode());

        return textToReturn.toString();
    }

    public void callAsyncTask(String json, List<ComicItem> listComics, Context context, MainDisplayAdapter mainDisplayAdapter){
        new ReadFileTask().execute(json, listComics, context, mainDisplayAdapter);
    }

    public List<ComicItem> getListComics() {
        return listComics;
    }

    public ComicItem getCurrentComics() {
        return currentComics;
    }

    public int getCurrentComicsPageCount() {
        return currentComics.getPageCount();
    }

    public String getCurrentComicsTitle() {
        return currentComics.getTitle();
    }

    public String getCurrentComicsDescription() {
        return currentComics.getDescription();
    }

    public String getCurrentComicsDiamondCode() {
        return currentComics.getDiamondCode();
    }

    public String getCurrentComicsDate() {
        return currentComics.getFormatedDate();
    }

    public String getCurrentComicsImageUrl() {
        return currentComics.getImage();
    }

    public List<Creator> getComicsCreators() {
        return currentComics.getCreators();
    }

    public String getWebUrl() {
        return currentComics.getUrl();
    }

}
