package com.example.mohamednagy.restaurant_project;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase sql;
    EditText userName;
    EditText passWord;
    Database db;
    Button login;
    Button register;
    public static String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.registerFromLogin);

        sql = openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);
        //db.dropUsertable();
        db.createTables();
        /*db.dropFoodtable();
        db.dropCategorytable();
        db.createTables();
        db.addCategory("Pizza");
        db.addCategory("Sea Food");
        db.addCategory("Burger");
        int id1 = getResources().getIdentifier("f1","drawable",getPackageName());
        db.addFood("Burger1","10$",id1,"Pizza");
        int id2 = getResources().getIdentifier("f2","drawable",getPackageName());
        db.addFood("Burger2","10$",id2,"Sea Food");
        int id3 = getResources().getIdentifier("f3","drawable",getPackageName());
        db.addFood("Burger3","10$",id3,"Burger");
        int id4 = getResources().getIdentifier("f1","drawable",getPackageName());
        db.addFood("Burger4","10$",id4,"Burger");
        int id5 = getResources().getIdentifier("f2","drawable",getPackageName());
        db.addFood("Burger5","10$",id5,"Sea Food");
        int id6 = getResources().getIdentifier("f3","drawable",getPackageName());
        db.addFood("Burger6","10$",id6,"Pizza");
        //  db.getFoods();*/
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v == login) {
            Log.e("LLLLLLLLLL","Login");
            if (userName.getText().length() > 0 && passWord.getText().length() > 0)
            {
                userData = db.checkLogin(userName.getText().toString(), passWord.getText().toString());
            }else if (userName.getText().length() == 0 || passWord.getText().length() == 0)
            {
                Toast.makeText(this , "FILL DATA \uD83D\uDE1E" , Toast.LENGTH_SHORT).show();
            }
            if (userData != null) {
                String Type = db.getType(Integer.parseInt(userData));
                Log.e("LLLLLLLLLL",Type);
                if( Type.equals("User")) {
                    Intent intent = new Intent(getBaseContext(), User_Activity.class);
                    Log.e("usrID ====>","   "+userData);
                    intent.putExtra("Id", userData);
                    startActivity(intent);
                }
                else if(Type.equals("Admin")){
                    Intent intent = new Intent(getBaseContext(), Admin_Activity.class);
                    intent.putExtra("Id", userData);
                    startActivity(intent);
                }
            }else
            {
                Toast.makeText(this , "Wrong Account \uD83D\uDE1E" , Toast.LENGTH_LONG).show();
            }
        }else if(v == register)
        {
            Intent intent = new Intent(getBaseContext(), Register.class);
            startActivity(intent);
        }

    }
    public void onPause()
    {
        super.onPause();
        finish();
    }



}

