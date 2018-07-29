package com.example.kwon.tracktrek;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ListViewItem {
    private String titleStr ;
    private String ContentStr;
    private Integer LatitudeStr;
    private Integer LongitudeStr;

    public void setTitle(String title) {
        titleStr = title;
    }
    public void setContent(String contentStr) {
        ContentStr = contentStr;
    }
    public void setLatitude(int latitudeStr){
        LatitudeStr = latitudeStr;
    }
    public void setLongitude(int longitudeStr){
        LongitudeStr = longitudeStr;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getContent() {
        return this.ContentStr;
    }
    public Integer getLatitude() {
        return this.LatitudeStr;
    }
    public Integer getLongitude() {
        return this.LongitudeStr;
    }

}

