package com.example.kwon.tracktrek;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ListViewItem {
    private String titleStr ;
    private String ContentStr;
    private Double LatitudeStr;
    private Double LongitudeStr;

    public void setTitle(String title) {
        titleStr = title;
    }
    public void setContent(String contentStr) {
        ContentStr = contentStr;
    }
    public void setLatitude(Double latitudeStr){
        LatitudeStr = latitudeStr;
    }
    public void setLongitude(Double longitudeStr){
        LongitudeStr = longitudeStr;
    }

    public void add(String title, String content, Double latitude, Double longitude){
        this.titleStr = title;
        this.ContentStr = content;
        this.LatitudeStr = latitude;
        this.LongitudeStr = longitude;
    }

    public void clear(){
        titleStr = null;
        ContentStr = null;
        LongitudeStr = null;
        LatitudeStr = null;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getContent() {
        return this.ContentStr;
    }
    public Double getLatitude() {
        return this.LatitudeStr;
    }
    public Double getLongitude() {
        return this.LongitudeStr;
    }

}

