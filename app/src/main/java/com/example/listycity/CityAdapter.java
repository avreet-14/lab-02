package com.example.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> cities;
    private boolean deleteMode = false;
    private ArrayList<Integer> checkedPositions = new ArrayList<>();

    public CityAdapter(Context context, ArrayList<String> cities) {
        super(context, 0, cities);
        this.context = context;
        this.cities = cities;
    }

    public void setDeleteMode(boolean mode) {
        deleteMode = mode;
        checkedPositions.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getCheckedPositions() {
        return checkedPositions;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.check_city, parent, false);
        }

        TextView cityName = convertView.findViewById(R.id.city_name);
        CheckBox checkBox = convertView.findViewById(R.id.city_checkbox);

        cityName.setText(cities.get(position));

        // Reset listener and state
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(checkedPositions.contains(position));

        if (deleteMode) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!checkedPositions.contains(position)) {
                    checkedPositions.add(position);
                }
            } else {
                checkedPositions.remove((Integer) position);
            }
        });

        return convertView;
    }
}