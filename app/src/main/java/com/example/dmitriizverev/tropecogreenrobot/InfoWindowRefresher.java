package com.example.dmitriizverev.tropecogreenrobot;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

/**
 * Created by dmitriizverev on 22/9/16.
 */

public class InfoWindowRefresher implements Callback {
    private Marker markerToRefresh;

    public InfoWindowRefresher(Marker markerToRefresh) {
        this.markerToRefresh = markerToRefresh;
    }

    @Override
    public void onSuccess() {
        markerToRefresh.showInfoWindow();
    }

    @Override
    public void onError() {}
}