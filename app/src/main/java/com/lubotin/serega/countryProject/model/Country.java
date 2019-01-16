package com.lubotin.serega.countryProject.model;

import com.lubotin.serega.countryProject.room.Converters;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Country {
    @PrimaryKey
    @NonNull
    private String countryName;
    @TypeConverters({Converters.class})
    private ArrayList<String> citiesList;

    public Country(@NonNull String countryName, ArrayList<String> citiesList) {
        this.countryName = countryName;
        this.citiesList = citiesList;
    }

    @NonNull
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NonNull String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<String> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(ArrayList<String> citiesList) {
        this.citiesList = citiesList;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
