package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class addFood extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {


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
        View view =  inflater.inflate(R.layout.fragment_add_food, container, false);
        if (container != null) {
            container.removeAllViewsInLayout();
        }

        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinnerAdd);
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);

        ArrayList<String> catArr = db.getCategory();
        Log.e("List ==>",catArr.toString());
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,catArr);
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
        int id = getResources().getIdentifier(foodPhoto.getText().toString(),"drawable",getActivity().getPackageName());
        db.addFood(foodName.getText().toString(),foodprice.getText().toString(),id,selectedCategory.getText().toString());
        Toast.makeText(getActivity(),"Food Added \uD83D\uDE0A",Toast.LENGTH_LONG).show();
    }
}