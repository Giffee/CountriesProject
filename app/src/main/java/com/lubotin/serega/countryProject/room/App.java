package com.lubotin.serega.countryProject.room;

import android.app.Application;

import com.lubotin.serega.countryProject.R;

import androidx.room.Room;

public class App extends Application {
    private static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, getString(R.string.databasename)).build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
