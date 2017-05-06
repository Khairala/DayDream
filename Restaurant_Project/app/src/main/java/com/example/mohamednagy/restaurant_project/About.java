package com.example.mohamednagy.restaurant_project;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.RatingBar;


public class About extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container != null)
        {
            container.removeAllViewsInLayout();
        }
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        ratingBar.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        return view;
    }

}
