package com.example.BarHop;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.BarHop.apicall.PlacesResponse;
import com.example.BarHop.apicall.Venue;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class browse_bars extends Fragment {
    private List<Bar> bars = new ArrayList<>();
    private SeekBar distanceBar;
    private TextView range_text;
    private Integer searchDistance;
    private static final int MAX_DISTANCE = 25;

    private static final String CLIENT_ID = ""; // TODO: remove client id from source
    private static final String CLIENT_SECRET = ""; // TODO: remove client secret from source
    private static final String BAR_CATEGORY_ID = "4bf58dd8d48988d116941735"; // FourSquare Places API 'Bar' category ID
    private static final String API_URL = "https://api.foursquare.com/v2/venues/search";

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        searchDistance = 1;

        View view = inflater.inflate(R.layout.activity_browse_bars,null);
        distanceBar = (SeekBar) view.findViewById(R.id.seekBar);
        range_text = (TextView) view.findViewById(R.id.range_text);

        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                int distance = Math.round((MAX_DISTANCE * (float) (progress / 100.00)));
                searchDistance = Math.max(1, distance);

                range_text.setText(searchDistance + " Miles");
                populateBarList();
            }
        });

        populateBarList();

        RecyclerView recyclerView = view.findViewById(R.id.bar_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), bars);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

        return view;
    }


    private void populateBarList() {
        try {
            bars.clear();
            Location currentPos = new Location("");
            currentPos.setLatitude(MainActivity.latitude);
            currentPos.setLongitude(MainActivity.longitude);

            searchDistance *= 1600;

            PlacesResponse bars1000m = makeAPICall(
                    MainActivity.latitude.toString(),
                    MainActivity.longitude.toString(),
                    searchDistance.toString());


            for(Venue v : bars1000m.response.venues) {
                Location barPos = new Location("");
                barPos.setLatitude(v.location.lat);
                barPos.setLongitude(v.location.lng);
                Float distance = currentPos.distanceTo(barPos) / 1600;

                Bar newBar = new Bar(
                        v.name,
                        v.location.address,
                        DECIMAL_FORMAT.format(distance) + " Miles");
                bars.add(newBar);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static PlacesResponse makeAPICall(String latitude, String longitude, String radius) throws Exception {
        // format URL endpoint according to latitude, longitude, and radius parameters
        URL url = new URL(
                API_URL + "?client_id=" + CLIENT_ID + "&client_secret="
                        + CLIENT_SECRET + "&v=20130815" + "&ll=" + latitude + "," + longitude
                        + "&categoryId=" + BAR_CATEGORY_ID + "&radius=" + radius
        );

        // send HTTP GET request to API endpoint w/ specified parameters
        String result = "";
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String curLine;
        while ((curLine = reader.readLine()) != null) {
            result = result + curLine;
        }
        reader.close();
        Gson gson = new Gson();
        try {
            PlacesResponse response = gson.fromJson(result, PlacesResponse.class);
            return response;
        } catch(Exception e) {
            System.out.println("Could not parse JSON response");
            e.printStackTrace();
            return null;
        }
    }
}
