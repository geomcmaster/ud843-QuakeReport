package com.example.android.quakereport;

/**
 * Created by Geoff on 12/18/2016.
 */

public class Earthquake {
    private Double mMagnitude;
    private String mPlace;
    private Long mDate;
    private String mUrl;

    public Earthquake(Double magnitude, String place, Long date, String url){
        mMagnitude = magnitude;
        mPlace = place;
        mDate = date;
        mUrl = url;
    }

    public Double getMagnitude() {return mMagnitude;}
    public String getPlace() {return mPlace;}
    public Long getDate() {return mDate;}
    public String getUrl() {return mUrl;}
}
