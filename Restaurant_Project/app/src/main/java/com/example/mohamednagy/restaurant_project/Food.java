package com.example.mohamednagy.restaurant_project;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food extends Fragment implements ValueEventListener{


    public Food() {
        // Required empty public constructor
    }
    SQLiteDatabase sql;
    RecyclerView recyclerView;
    Database db;
    ArrayList<FoodItem> foodItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.viewlist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);
        foodItems = db.getAllfood();
        FoodAdapter foodAdapter = new FoodAdapter(this.foodItems);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.addValueEventListener(this);

        return view;
    }

    public void sendNotification(String Body){
        Intent intent = new Intent(getActivity(), Food.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0 , intent,PendingIntent.FLAG_ONE_SHOT);
        // sound
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.about)
                .setContentTitle("DayDream")
                .setContentText(Body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , builder.build());
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // This method is called once with the initial value and again
        // whenever data at this location is updated.
        String value = dataSnapshot.getValue(String.class);

        if(value != null ) {
            sendNotification("A new Item Added \uD83D\uDE0A" + value);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Failed to read value
        Log.w("Fail", "Failed to read value.", databaseError.toException());

    }

}
