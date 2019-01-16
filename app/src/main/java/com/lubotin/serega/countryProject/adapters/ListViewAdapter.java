package com.lubotin.serega.countryProject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lubotin.serega.countryProject.CityActivity;
import com.lubotin.serega.countryProject.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ListViewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);
        }

        String city = list.get(position);
        TextView textView = v.findViewById(R.id.cities);
        textView.setText(city);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCityIntent();
                Intent intent = new Intent(context, CityActivity.class);
                intent.putExtra("cityName", city);
                context.startActivity(intent);
            }
        });
        return v;
    }

    private void goCityIntent() {
    }
}
