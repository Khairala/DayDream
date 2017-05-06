package com.example.mohamednagy.restaurant_project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText userName = (EditText) findViewById(R.id.userName);
        final EditText passWord = (EditText) findViewById(R.id.passWord);
        final Button login = (Button) findViewById(R.id.login);
        sql = openOrCreateDatabase("myDB" ,0,null);
        final Database db = new Database(sql);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> userData = db.checkLogin(userName.getText().toString() , passWord.getText().toString());
                if(userData != null)
                {
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
        });
    }

    @Override
    public void onClick(View v) {
        
    }
}

