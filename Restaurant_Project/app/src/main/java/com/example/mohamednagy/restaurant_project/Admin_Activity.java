package com.example.mohamednagy.restaurant_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Activity extends AppCompatActivity{

    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);


        Bundle bundle = getIntent().getExtras();
        UserProfile userProfile = new UserProfile();
        userProfile.setArguments(bundle);


        Toolbar adminToolbar = (Toolbar) findViewById(R.id.admintoolbar);
        setSupportActionBar(adminToolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflate = getMenuInflater();
        menuInflate.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(item.getTitle().equals("Add Category"))
        {
            add_category cat = new add_category();
            ft.replace(R.id.fragmentContentAdmin,cat);
            ft.commit();

        }else if (item.getTitle().equals("Delete Category"))
        {
            delete_category delete_category = new delete_category();
            ft.replace(R.id.fragmentContentAdmin,delete_category);
            ft.commit();
        }
        else if (item.getTitle().equals("Delete Food"))
        {
            deleteFood delFood = new deleteFood();
            ft.replace(R.id.fragmentContentAdmin,delFood);
            ft.commit();
        }else if (item.getTitle().equals("Add Food"))
        {
            addFood addFood = new addFood();
            ft.replace(R.id.fragmentContentAdmin,addFood);
            ft.commit();
        }
        else if (item.getTitle().equals("Profile"))
        {
            UserProfile prof = new UserProfile();
            Bundle bundle = this.getIntent().getExtras();
            prof.setArguments(bundle);
            ft.replace(R.id.fragmentContentAdmin,prof);
            ft.commit();
        }
        else if(item.getTitle().equals("Sign Out"))
        {
            finish();
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
