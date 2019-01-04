package com.lpiem.coderpropmarvelapp.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ComicItem implements Serializable {
    private final String title;
    private final String description;
    private final String image;
    private final String diamondCode;
    private final Date date;
    private final List<Creator> creators;
    private final int pageCount;

    public ComicItem(String title, String description, String image, String diamondCode, Date date, List<Creator> creators, int pageCount) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.diamondCode = diamondCode;
        this.date = date;
        this.creators = creators;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public Date getDate() {
        return date;
    }

    public String getFormatedDate() {
        DateFormat formatDay = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.FRANCE);
        String formatedDate = formatDay.format(date);
        formatedDate = formatedDate.substring(0,1).toUpperCase().concat(formatedDate.substring(1, formatedDate.length()));
        return formatedDate;
    }

    public List<Creator> getCreators() {
        return creators;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ComicItem{")
                .append("title='")
                .append(getTitle())
                .append('\'')
                .append(", description='")
                .append(getDescription())
                .append('\'')
                .append(", image='")
                .append(getImage())
                .append('\'')
                .append(", diamondCode='")
                .append(getDiamondCode())
                .append('\'')
                .append(", date=")
                .append(getFormatedDate())
                .append(", creators=")
                .append(getCreators())
                .append(", pageCount=")
                .append(getPageCount())
                .append('}')
                .toString();
    }
}
