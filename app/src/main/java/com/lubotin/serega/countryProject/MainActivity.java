package com.lubotin.serega.countryProject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lubotin.serega.countryProject.adapters.ListViewAdapter;
import com.lubotin.serega.countryProject.adapters.SpinnerAdapter;
import com.lubotin.serega.countryProject.api.RestClient;
import com.lubotin.serega.countryProject.model.Country;
import com.lubotin.serega.countryProject.room.App;
import com.lubotin.serega.countryProject.room.CountryDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements Callback<Object>, AdapterView.OnItemSelectedListener {

    public final String LOG_TAG = "LOGGING: ";
    private Spinner spinner;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listView);

        RestClient.getCountryInstance().getCountriesJson().enqueue(this);
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        String json = response.body().toString();

        List<Country> countriesListFromJson = parseJson(json);
        clearEmptyCountry(countriesListFromJson);
        sortingCountries(countriesListFromJson);
        sortingCities(countriesListFromJson);
        countriesToDB(countriesListFromJson);
        List<Country> countriesFromDB = new ArrayList<>(getAllFromDB());
        loadDataIntoDropdown(countriesFromDB);
    }



    @Override
    public void onFailure(Call<Object> call, Throwable t) {
        logging("Something went wrong! Message: " + t.getMessage() + "||| Cause: " + t.getCause());
    }

    private void sortingCountries(List<Country> countriesListFromJson) {
        Collections.sort(countriesListFromJson, (o1, o2) -> o1.getCountryName().compareToIgnoreCase(o2.getCountryName()));
    }

    private void sortingCities(List<Country> countriesListFromJson) {
        for (Country c : countriesListFromJson) {
            Collections.sort(c.getCitiesList(), String::compareToIgnoreCase);
        }
    }

    private void loadDataIntoDropdown(List<Country> countriesFromDB) {
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,
                android.R.layout.simple_spinner_item, countriesFromDB);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextSize(22);
        List<String> citiesList = ((Country) parent.getItemAtPosition(position)).getCitiesList();
        ListViewAdapter adapter = new ListViewAdapter(citiesList, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private List<Country> getAllFromDB() {
        List<Country> list = new ArrayList<>();
        Thread thread = new Thread(() -> {
            CountryDao dao = App.getInstance().getDatabase().countryDao();
            list.addAll(dao.getAll());
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void clearEmptyCountry(List<Country> countriesList) {
        for (int i = 0; i < countriesList.size(); i++) {
            if (countriesList.get(i).getCountryName().equals("")) {
                countriesList.remove(i);
                i--;
            }
        }
    }

    private void countriesToDB(List<Country> countriesList) {
        Thread thread = new Thread(() -> {
            CountryDao dao = App.getInstance().getDatabase().countryDao();
            for (Country c : countriesList) {
                dao.upsert(c);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Country> parseJson(String json) {
        List<Country> list = new ArrayList<>();

        Pattern pattern = Pattern.compile("([^{},]*?)=\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            ArrayList<String> citiesList = new ArrayList<>();
            for (String city : matcher.group(2).split(",")) {
                citiesList.add(city.trim());
            }
            String countryName = matcher.group(1).trim();
            Country country = new Country(countryName, citiesList);
            list.add(country);
        }
        return list;
    }

    public void logging(String str) {
        Log.d(LOG_TAG, str);
    }
}
