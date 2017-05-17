package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class deleteFood extends Fragment implements AdapterView.OnItemClickListener {

    ListView foodList;
    SQLiteDatabase sql;
    Database db;
    ArrayList<String> foodArr;
    ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        View view = inflater.inflate(R.layout.fragment_delete_food, container, false);
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);

        foodList = (ListView) view.findViewById(R.id.delFoodlist);
        foodArr = db.getAllfoodAdmin();
        if (foodArr != null)
        {
            arrayAdapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_list_item_1 , foodArr);
            foodList.setAdapter(arrayAdapter);
            foodList.setOnItemClickListener(this);
        }else
        {
            Toast.makeText(getActivity() , "ADD FOOD FIRST" , Toast.LENGTH_LONG).show();
        }



        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        db.deleteFood(foodArr.get(position));
        foodArr.remove(position);
        arrayAdapter.notifyDataSetChanged();
    }
}