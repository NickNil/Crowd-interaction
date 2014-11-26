package hig.no.crowdinteraction;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Mimoza on 11/19/2014.
 */
public class EventMap extends FragmentActivity {


    String SERVER_URL = "http://ci.harnys.net";

    public GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);

        setUpMapIfNeeded();
        sendJson();


    }

    protected void sendJson() {

        Thread t = new Thread() {

            public void run() {

                HttpClient client = new DefaultHttpClient();
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(SERVER_URL + "/api/events");

                    response = client.execute(post);

                    if (response != null) {

                        HttpEntity entity;
                        entity = response.getEntity();

                        InputStream in = entity.getContent(); //Get the data in the entity
                        StatusLine statusLine = response.getStatusLine();
                        int statusCode = statusLine.getStatusCode();
                        Log.i("HTTP Status", Integer.toString(statusCode));

                        String jsonString = inputStreamToString(in);
                        jsonString = jsonString.replaceFirst(Pattern.quote("["), "");

                        JSONObject jsonObj = new JSONObject(jsonString);
                        JSONObject data = jsonObj.getJSONObject("data");

                        String[] id = new String[]{"5464cdcc9c7684e324ff4ccb","54281393e4b0b7147c91f492","542a8c27a77c9e3cb8d63af2"};
                        Double latitude, longitude;
                        final LatLng[] latLong = new LatLng[3];
                        final String[] eventNames = new String[3];

                        for(int i = 0; i<id.length; i++) {
                            JSONObject ID = data.getJSONObject(id[i]);
                            JSONObject eventData = ID.getJSONObject("event_data");
                            String eventName = eventData.getString("event_name");
                            eventNames[i] = eventName;

                            final JSONObject location = eventData.getJSONObject("event_location");
                            Log.i("location", location.toString());
                            latitude = Double.parseDouble(location.get("latitude").toString());
                            longitude = Double.parseDouble(location.get("longitude").toString());
                            latLong[i] = new LatLng(latitude, longitude);
                        }


                        final Integer[] icons = new Integer[]{ R.drawable.figure_skating, R.drawable.ski_jump, R.drawable.snowboard};

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                for(int i = 0; i<latLong.length; i++){

                                    mMap.addMarker(new MarkerOptions()
                                            .position(latLong[i])
                                            .title(eventNames[i])
                                            .icon(BitmapDescriptorFactory.fromResource(icons[i])));
                                }
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLong[1]).zoom(11).build();
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }
                        });


                        in.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };

        t.start();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    }
                });

            }
        }
    }
    private String inputStreamToString(InputStream is) {
        String rLine;
        StringBuilder answer = new StringBuilder();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);

            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }

        } catch (Exception e) {
        }
        return answer.toString();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
}

