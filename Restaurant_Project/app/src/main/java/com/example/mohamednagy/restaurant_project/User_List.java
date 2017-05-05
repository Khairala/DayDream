package com.example.mohamednagy.restaurant_project;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class User_List extends Fragment implements AdapterView.OnItemClickListener{


    public User_List() {
        // Required empty public constructor
    }
    ArrayList<String> listContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_user__list, container, false);
        listContent = new ArrayList<>();
        listContent.add("Profile");
        listContent.add("Food");
        listContent.add("My Orders");
        listContent.add("Feedback");
        listContent.add("Sign Out");
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listContent);
        ListView userList =(ListView) view.findViewById(R.id.userList);
        userList.setAdapter(adapterList);
        userList.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
            if(listContent.get(i) == "Food")
            {
                Food food = new Food();
                ft.replace(R.id.fragmentContent,food);
                ft.commit();
            }else if (listContent.get(i) == "Profile")
            {
                Profile prof = new Profile();
                ft.replace(R.id.fragmentContent,prof);
                ft.commit();
            }

    }
}
