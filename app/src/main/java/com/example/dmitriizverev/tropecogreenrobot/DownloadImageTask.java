package com.example.dmitriizverev.tropecogreenrobot;

/**
 * Created by dmitriizverev on 22/9/16.
 */
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import  android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.Marker;

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Marker marker;
    View v;

    public DownloadImageTask(ImageView bmImage, Marker marker,View v) {
        this.bmImage = bmImage;
        this.marker = marker;
        this.v = v;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bmImage.setImageBitmap(result);
        marker.showInfoWindow();
    }
}
