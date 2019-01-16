package com.lubotin.serega.countryProject.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String COUNTRIES_BASE_URL = "https://raw.githubusercontent.com/";

    private static final RestClient instance = new RestClient();

    public static Api getCountryInstance() {
        return instance.countryService;
    }

    private final Api countryService;

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(COUNTRIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        countryService = retrofit.create(Api.class);

    }

}
