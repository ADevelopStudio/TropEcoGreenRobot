package com.example.dmitriizverev.tropecogreenrobot;

import java.io.*;

/**
 * Created by dmitriizverev on 21/9/16.
 */

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.annotations.SerializedName;

public class Point implements Serializable{
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("_id")
    public String identidicator;
    @SerializedName("isApproved")
    public boolean isApproved;
    @SerializedName("isFlagged")
    public boolean isFlagged;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("category")
    public String category;

    public boolean isValid() {
        return title.length() > 0 && (isApproved || !isFlagged);
    }

    public BitmapDescriptor geoIcon(){
        switch(category){
            case "Trees": return BitmapDescriptorFactory.fromResource(R.drawable.trees_pin);
            case "Animals": return BitmapDescriptorFactory.fromResource(R.drawable.animals_pin);
            default: return BitmapDescriptorFactory.fromResource(R.drawable.others_pin);
        }
    }
    public int infoIcon(){
        switch(category){
            case "Trees": return R.drawable.trees_logo;
            case "Animals": return R.drawable.animals_logo;
            default: return R.drawable.others_logo;
        }
    }
}
