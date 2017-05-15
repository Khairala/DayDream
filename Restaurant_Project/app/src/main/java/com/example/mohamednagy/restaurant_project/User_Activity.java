package com.example.mohamednagy.restaurant_project;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_layout);

        Bundle bundle = getIntent().getExtras();
        Profile profile = new Profile();
        profile.setArguments(bundle);

        Toolbar userToolbar = (Toolbar) findViewById(R.id.usertoolbar);
        setSupportActionBar(userToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflate = getMenuInflater();
        menuInflate.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(item.getTitle().equals("Food"))
        {
            Food food = new Food();
            ft.replace(R.id.fragmentContent,food);
            ft.commit();
        }else if (item.getTitle().equals("Profile"))
        {
            Profile prof = new Profile();
            Bundle bundle = this.getIntent().getExtras();
            prof.setArguments(bundle);
            ft.replace(R.id.fragmentContent,prof);
            ft.commit();
        }else if (item.getTitle().equals("About Us"))
        {
            About about = new About();

            ft.replace(R.id.fragmentContent,about);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
