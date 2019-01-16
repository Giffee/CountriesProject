package com.lubotin.serega.countryProject.room;

import com.lubotin.serega.countryProject.model.Country;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(exportSchema = false, entities = {Country.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}
