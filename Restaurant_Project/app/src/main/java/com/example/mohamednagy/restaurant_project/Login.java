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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener, ValueEventListener, ChildEventListener {
    SQLiteDatabase sql;
    EditText userName;
    EditText passWord;
    Database db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        final Button login = (Button) findViewById(R.id.login);
        sql = openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);
       // db.dropFoodtable();
        //db.dropCategorytable();
        //db.createTables();
        db.addCategory("category11");
        db.addCategory("category2");
        db.addCategory("category3");
        //db.addUser();
        /*int id1 = getResources().getIdentifier("f1","drawable",getPackageName());
        db.addFood("Burger","10$",id1,"category1");
        int id2 = getResources().getIdentifier("f2","drawable",getPackageName());
        db.addFood("Burger","10$",id2,"category2");
        int id3 = getResources().getIdentifier("f3","drawable",getPackageName());
        db.addFood("Burger","10$",id3,"category3");*/
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        myRef.addValueEventListener(this);
        myRef.addChildEventListener(this);
        login.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View v) {
        String userData = db.checkLogin(userName.getText().toString(), passWord.getText().toString());
        if (userData != null) {
            Intent intent = new Intent(getBaseContext(), User_Activity.class);
            intent.putExtra("Id", userData);
            startActivity(intent);
        }
    }

    public void sendNotification(String Body) {
        Intent intent = new Intent(this, Food.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        // sound
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.about)
                .setContentTitle("DayDream")
                .setContentText(Body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        sendNotification("A new Item Added \uD83D\uDE0A" + dataSnapshot.getValue(String.class));
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Failed to read value
        Log.w("Fail", "Failed to read value.", databaseError.toException());

    }


}

