package com.example.mohamednagy.restaurant_project;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food extends Fragment implements AdapterView.OnItemSelectedListener{


    public Food() {
        // Required empty public constructor
    }
    SQLiteDatabase sql;
    RecyclerView recyclerView;
    Database db;
    ArrayAdapter<String> adapter;
    Spinner categorySpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.viewlist);
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);

        ArrayList<String> catArr = db.getCategory();
        Log.e("List ==>",catArr.toString());
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,catArr);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);
        ArrayList<FoodItem> foodList = db.getAllfood("Pizza");
        //Log.e("DDDDDDDDDDD",foodList.toString());
        FoodAdapter foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) view;
        ArrayList<FoodItem> foodList = db.getAllfood(txt.getText().toString());

        FoodAdapter foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
