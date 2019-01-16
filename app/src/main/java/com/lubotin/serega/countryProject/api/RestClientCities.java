package com.lubotin.serega.countryProject.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientCities {
    private static final String COUNTRIES_BASE_URL = "http://api.geonames.org/";

    private static final RestClientCities instance = new RestClientCities();

    public static ApiCities getCountryInstance() {
        return instance.countryService;
    }

    private final ApiCities countryService;

    private RestClientCities() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(COUNTRIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        countryService = retrofit.create(ApiCities.class);

    }


}
