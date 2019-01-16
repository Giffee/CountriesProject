package com.lubotin.serega.countryProject.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lubotin.serega.countryProject.R;
import com.lubotin.serega.countryProject.model.Country;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter extends ArrayAdapter<Country> {
    private Activity context;
    List<Country> data;

    public SpinnerAdapter(@NonNull Activity context, int resource, @NonNull List<Country> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        Country item = data.get(position);

        if (item != null) {
            TextView myCountry = row.findViewById(R.id.countryName);
            if (myCountry != null)
                myCountry.setText(item.getCountryName());
        }

        return row;
    }
}
