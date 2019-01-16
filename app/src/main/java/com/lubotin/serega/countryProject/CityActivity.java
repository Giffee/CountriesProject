package com.lubotin.serega.countryProject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lubotin.serega.countryProject.api.RestClientCities;
import com.lubotin.serega.countryProject.model.City;
import com.lubotin.serega.countryProject.model.Geoname;

public class CityActivity extends Activity implements Callback<City> {
    public static final int MAX_ROWS = 1;
    public static final String USERNAME = "giffy";

    private TextView summary;
    private TextView countryCode;
    private TextView longitude;
    private TextView latitude;
    private TextView wikipediaUrl;

    public final String LOG_TAG = "LOGGING: ";

    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        TextView cityName = findViewById(R.id.cityName);
        summary = findViewById(R.id.summary);
        countryCode = findViewById(R.id.countryCode);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        wikipediaUrl = findViewById(R.id.wikipediaUrl);
        wikipediaUrl.setClickable(true);

        Intent intent = getIntent();
        city = intent.getStringExtra("cityName");
        cityName.setText(intent.getStringExtra("cityName"));

        RestClientCities.getCountryInstance().getDescriptionJson(city, MAX_ROWS, USERNAME).enqueue(this);
    }

    @Override
    public void onResponse(Call<City> call, Response<City> response) {
        Geoname geoname = response.body().getGeonames().get(0);
        summary.setText(geoname.getSummary());
        countryCode.setText(geoname.getCountryCode());
        longitude.setText(String.valueOf(geoname.getLng()));
        latitude.setText(String.valueOf(geoname.getLat()));
        wikipediaUrl.setText(geoname.getWikipediaUrl());
    }

    @Override
    public void onFailure(Call<City> call, Throwable t) {
        logging("Something went wrong! Message: " + t.getMessage() + "||| Cause: " + t.getCause());
    }

    public void logging(String str) {
        Log.d(LOG_TAG, str);
    }
}
