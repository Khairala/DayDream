package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food extends Fragment {


    public Food() {
        // Required empty public constructor
    }
    SQLiteDatabase sql;
    RecyclerView recyclerView;
    Database db;
    ArrayList<FoodItem> foodItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.viewlist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);
        foodItems = db.getAllfood();
        FoodAdapter foodAdapter = new FoodAdapter(this.foodItems);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

}
