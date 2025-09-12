package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton, deleteButton, cancelDeleteButton, confirmDeleteButton;
    boolean deleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_city);
        deleteButton = findViewById(R.id.delete_city);
        cancelDeleteButton = findViewById(R.id.cancel_delete);
        confirmDeleteButton = findViewById(R.id.confirm_delete);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        cityAdapter = new CityAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add city
        addButton.setOnClickListener(v -> showAddCityDialog());

        // Delete mode toggle
        deleteButton.setOnClickListener(v -> toggleDeleteMode());

        // Cancel delete
        cancelDeleteButton.setOnClickListener(v -> exitDeleteMode(false));

        // Confirm delete
        confirmDeleteButton.setOnClickListener(v -> exitDeleteMode(true));
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);
        input.setHint("Enter city name");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String city = input.getText().toString().trim();
                    if (!city.isEmpty()) {
                        dataList.add(city);
                        cityAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void toggleDeleteMode() {
        if (!deleteMode) {
            deleteMode = true;
            ((CityAdapter) cityAdapter).setDeleteMode(true);
            findViewById(R.id.delete_bar).setVisibility(android.view.View.VISIBLE);
        }
    }

    private void exitDeleteMode(boolean confirm) {
        if (confirm) {
            ArrayList<Integer> checkedPositions = ((CityAdapter) cityAdapter).getCheckedPositions();
            checkedPositions.sort((a, b) -> b - a);
            for (int pos : checkedPositions) {
                dataList.remove(pos);
            }
            cityAdapter.notifyDataSetChanged();
        }
        ((CityAdapter) cityAdapter).setDeleteMode(false);
        deleteMode = false;
        findViewById(R.id.delete_bar).setVisibility(android.view.View.GONE);
    }
}