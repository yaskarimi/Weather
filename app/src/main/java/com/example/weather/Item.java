package com.example.weather;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

public class Item {
    int tempMax;
    int tempMin;
    Bitmap icon;
    String month;
    int day;
    String dayOfWeek;
    String imageUrl;

    int getTempMax(){
        return tempMax;
    }
    void setTempMax(int tempMax){
        this.tempMax=tempMax;
    }
    int getTempMin(){
        return tempMin;
    }
    void setTempMin(int tempMin){
        this.tempMin=tempMin;
    }
    Bitmap getIcon(){
        return icon;
    }
    void setIcon(Bitmap icon){
        this.icon=icon;
    }
    int getDay(){
        return day;
    }
    void setDay(int day){
        this.day = day;
    }
    String getDayOfWeek(){
        return dayOfWeek;
    }
    void setDayOfWeek(String dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }
    String getMonth(){
        return month;
    }
    void setMonth(String month){
        this.month = month;
    }

}
