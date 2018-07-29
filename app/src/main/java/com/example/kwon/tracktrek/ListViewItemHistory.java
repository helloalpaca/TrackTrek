package com.example.kwon.tracktrek;

public class ListViewItemHistory {
    private String titleStr ;
    private String startDayStr;
    private String endDayStr;

    ListViewItemHistory() { }

    ListViewItemHistory(String title, String startDay, String endDay ){
        this.titleStr = title;
        this.startDayStr = startDay;
        this.endDayStr = endDay;
    }

    public void setTitle(String title) {
        titleStr = title;
    }
    public void setStartDay(String startDay) {
        startDayStr = startDay;
    }
    public void setEndDay(String endDay) {
        endDayStr = endDay;
    }

    public void add(String title, String startDay, String endDay){
        this.titleStr = title;
        this.startDayStr = startDay;
        this.endDayStr = endDay;
    }

    public void clear(){
        titleStr = null;
        startDayStr = null;
        endDayStr = null;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getStartDay() {
        return this.startDayStr;
    }
    public String getEndDay() {
        return this.endDayStr;
    }
}
