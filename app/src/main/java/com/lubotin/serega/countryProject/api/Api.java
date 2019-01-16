package com.lubotin.serega.countryProject.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
    Call<Object> getCountriesJson();
}
