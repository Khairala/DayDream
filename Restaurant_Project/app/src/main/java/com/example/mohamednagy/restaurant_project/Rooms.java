package com.example.mohamednagy.restaurant_project;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import static com.example.mohamednagy.restaurant_project.Login.userData;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rooms extends Fragment {


    public Rooms() {
        // Required empty public constructor
    }
    SQLiteDatabase sql;
    Database db;

    Button addroom;
    EditText addcontact;
    ListView roomList;
    ArrayAdapter<String> ChatsAdapter;
    ArrayList<String> ListOfChats = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);

        addroom = (Button) view.findViewById(R.id.AddRoom);
        addcontact = (EditText) view.findViewById(R.id.roomName);
        roomList = (ListView) view.findViewById(R.id.roomList);

        ChatsAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ListOfChats);
        roomList.setAdapter(ChatsAdapter);

        addroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("kkkkkkkkkkkk",root.toString());
                Map<String,Object> map = new HashMap<String,Object>();
                map.put(addcontact.getText().toString(),"");
                root.updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> setofchats = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext())
                {
                    setofchats.add(((DataSnapshot) i.next()).getKey());
                }
                ListOfChats.clear();
                ListOfChats.addAll(setofchats);

                ChatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                chatRoom room = new chatRoom();
                ArrayList<String> user = db.getUser(Integer.parseInt(userData));
                Bundle bundle = new Bundle();
                bundle.putString("RoomName",((TextView) view).getText().toString());
                bundle.putString("UserName" , user.get(0));
                room.setArguments(bundle);
                ft.replace(R.id.fragmentContent,room);
                ft.commit();
            }
        });

        return view;
    }

}
