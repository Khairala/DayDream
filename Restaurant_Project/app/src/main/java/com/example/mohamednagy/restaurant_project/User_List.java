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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class User_List extends Fragment implements AdapterView.OnItemSelectedListener{


    public User_List() {
        // Required empty public constructor
    }
    ArrayList<String> spinnerContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_user__list, container, false);
        spinnerContent = new ArrayList<>();
        spinnerContent.add("Profile");
        spinnerContent.add("Food");
        spinnerContent.add("My Orders");
        spinnerContent.add("Feedback");
        spinnerContent.add("About");
        spinnerContent.add("Sign Out");
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item, spinnerContent);
        Spinner spinner =(Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapterList);
        spinner.setOnItemSelectedListener(this);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) view;
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
            if(txt.getText().toString() == "Food")
            {
                Food food = new Food();
                ft.replace(R.id.fragmentContent,food);
                ft.commit();
            }else if (txt.getText().toString() == "Profile")
            {
                Profile prof = new Profile();
                Bundle bundle = getActivity().getIntent().getExtras();
                prof.setArguments(bundle);

                ft.replace(R.id.fragmentContent,prof);
                ft.commit();
            }else if (txt.getText().toString() == "About")
            {
                About about = new About();
                ft.replace(R.id.fragmentContent,about);
                ft.commit();
            }


    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
