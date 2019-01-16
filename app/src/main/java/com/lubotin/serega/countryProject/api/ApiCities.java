package com.lubotin.serega.countryProject.api;

import com.lubotin.serega.countryProject.model.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCities {
    @GET("wikipediaSearchJSON")
    Call<City> getDescriptionJson(@Query("q") String q,
                                  @Query("maxRows") int maxRows,
                                  @Query("username") String username);
}
