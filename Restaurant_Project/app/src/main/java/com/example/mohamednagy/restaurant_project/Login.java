package com.example.mohamednagy.restaurant_project;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase sql;
    EditText userName;
    EditText passWord;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        final Button login = (Button) findViewById(R.id.login);
        sql = openOrCreateDatabase("myDB" ,0,null);
        db = new Database(sql);
        //db.dropTables();
        //db.createTables();
        //db.addUser();
        //db.dropTablesfood();
        db.createfoodtable();
        // int id = getResources().getIdentifier("f3","drawable",getPackageName());
        //db.addFood("Rice",id);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> userData = db.checkLogin(userName.getText().toString() , passWord.getText().toString());
        if(userData != null)
        {
            Log.e("mmmmmmmmmmmm","kkkkkkkkk");
            Bundle bundle = new Bundle();
            bundle.putString("userName", userData.get(0));
            bundle.putString("Email", userData.get(1));
            bundle.putString("Password", userData.get(2));
            bundle.putString("Address", userData.get(3));
            bundle.putString("Phone", userData.get(4));
            Profile fragobj = new Profile();
            fragobj.setArguments(bundle);
            Intent intent = new Intent(getBaseContext() , User_Activity.class);
            startActivity(intent);
        }
    }
}

