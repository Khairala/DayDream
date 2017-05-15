package com.example.mohamednagy.restaurant_project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener,ValueEventListener {
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
       // db.dropTablesfood();
        db.createfoodtable();
       //  int id = getResources().getIdentifier("f2","drawable",getPackageName());
        //db.addFood("Burger","10$",id);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // Read from the database
        myRef.addValueEventListener(this);
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


    public void sendNotification(String Body){
        Intent intent = new Intent(this,User_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 , intent,PendingIntent.FLAG_ONE_SHOT);

        // sound
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.about)
                .setContentTitle("DayDream")
                .setContentText(Body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , builder.build());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);
        Log.e("Nagy", "Value is: " + value);
        sendNotification("A new Item Added :)");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Failed to read value
        Log.w("Nagy", "Failed to read value.", databaseError.toException());

    }
}

