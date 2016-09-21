package com.example.dmitriizverev.tropecogreenrobot;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.*;
import  android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import org.json.*;
import com.google.gson.*;
import cz.msebera.android.httpclient.entity.mime.Header;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public LatLng townsvilleCampus = new LatLng(-19.327641, 146.758327);

    private GoogleMap mMap;
    private Map<Marker, Point> markersMap = new HashMap<Marker, Point>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        getData(googleMap);
    }

    public void getData(final GoogleMap googleMap) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://tropeco.herokuapp.com/api/coordinates", new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.i("TEST", "onStart");
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                Log.i("TEST", "response" + response.toString());
                Gson gson1 = new Gson();
                Point[] geoPoints = gson1.fromJson(response.toString(), Point[].class);
                Log.i("TEST", "titlee2:" + geoPoints.length);

                mMap = googleMap;
                for (Point geoPoint : geoPoints) {
                    if (geoPoint.isValid()){
                        LatLng pointLocation = new LatLng(geoPoint.latitude, geoPoint.longitude);
                        MarkerOptions markerus = new MarkerOptions().position(pointLocation).title(geoPoint.title)
                                .icon(geoPoint.geoIcon());
                        markerus.snippet(geoPoint.description);
                        Marker mrk = mMap.addMarker(markerus);
                        markersMap.put(mrk, geoPoint);

                    }
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(townsvilleCampus));
                mMap.animateCamera( CameraUpdateFactory.zoomTo(12.0f ) );

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        // Getting view from the layout file infowindowlayout.xml
                        View v = getLayoutInflater().inflate(R.layout.custom_info_view, null);
//                        LatLng latLng = arg0.getPosition();
                        ImageView im = (ImageView) v.findViewById(R.id.imageViewLogo);
                        TextView tv1 = (TextView) v.findViewById(R.id.textViewTitle);
                        TextView tv2 = (TextView) v.findViewById(R.id.textViewDescr);
                        String title = marker.getTitle();
                        String informations = marker.getSnippet();
                        tv1.setText(title);
                        tv2.setText(informations);
                        Point pnt =  markersMap.get(marker);
                        if (pnt != null) {
                            im.setImageResource(pnt.infoIcon());
                        }
//                        new DownloadImageTask(im, marker, v)
//                                .execute("https://image.freepik.com/free-icon/tree_318-139420.jpg");
                        return v;
                    }
                });

            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }
}
