package com.example.mohamednagy.restaurant_project;


import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */


    DatabaseReference myRef;
    SQLiteDatabase sql;
    Database db;
    ArrayAdapter<String> adapter;
    Spinner categorySpinner;
    EditText foodName;
    EditText foodprice;
    EditText foodPhoto;
    Button AddFood;
    TextView selectedCategory;


    public addFood() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        if (container != null) {
            container.removeAllViewsInLayout();
        }

        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinnerAdd);
        sql = getActivity().openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);


        ArrayList<String> catArr = db.getCategory();
        Log.e("List ==>", catArr.toString());
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, catArr);
        categorySpinner.setAdapter(adapter);
        foodName = (EditText) view.findViewById(R.id.AddFoodName);
        foodprice = (EditText) view.findViewById(R.id.AddPrice);
        foodPhoto = (EditText) view.findViewById(R.id.AddPhoto);
        AddFood = (Button) view.findViewById(R.id.AddFoodbtn);
        categorySpinner.setOnItemSelectedListener(this);
        AddFood.setOnClickListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = (TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (db.checkAvilabilty(foodName.getText().toString(), "food", "food_name") && foodName.getText().length() > 0 &&
                foodprice.getText().length() > 0 && foodPhoto.getText().length() > 0) {
            int id = getResources().getIdentifier(foodPhoto.getText().toString(), "drawable", getActivity().getPackageName());
            db.addFood(foodName.getText().toString(), foodprice.getText().toString(), id, selectedCategory.getText().toString());
            Toast.makeText(getActivity(), "Food Added \uD83D\uDE0A", Toast.LENGTH_LONG).show();
        } else if (!db.checkAvilabilty(foodName.getText().toString(), "food", "food_name")) {
            Toast.makeText(getActivity(), "Food is Found \uD83D\uDE1E", Toast.LENGTH_SHORT).show();
        } else if (foodName.getText().length() == 0 || foodprice.getText().length() == 0 || foodPhoto.getText().length() == 0) {
            Toast.makeText(getActivity(), "ERROR !!!", Toast.LENGTH_SHORT).show();
        }

    }


}
