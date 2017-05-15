package com.example.mohamednagy.restaurant_project;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Activity extends AppCompatActivity implements ValueEventListener {

    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_layout);

        Bundle bundle = getIntent().getExtras();
        Profile profile = new Profile();
        profile.setArguments(bundle);

        TextView holder = (TextView) findViewById(R.id.holder);
        holder.setText(bundle.getString("Id"));
        Log.e("LLLLLLLLLLLLLLLLL",bundle.getString("Id"));
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        myRef.addValueEventListener(this);

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
        else if (item.getTitle().equals("Orders"))
        {
            Orders order = new Orders();

            ft.replace(R.id.fragmentContent,order);
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
        sendNotification("A new Food Added \uD83D\uDE0A"+value);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Failed to read value
        Log.w("Fail", "Failed to read value.", databaseError.toException());

    }


}
