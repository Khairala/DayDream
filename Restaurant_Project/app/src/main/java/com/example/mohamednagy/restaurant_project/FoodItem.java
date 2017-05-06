package com.example.mohamednagy.restaurant_project;

import android.util.Log;

/**
 * Created by MohamedNagy on 5/6/2017.
 */

public class FoodItem {
    public String title;
    public String price;
    public int imageUrl;

    public FoodItem(String t,String price,int i)
    {
        this.title = t;
        this.imageUrl = i;
        this.price = price;
    }
}
