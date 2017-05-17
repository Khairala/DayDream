package com.example.mohamednagy.restaurant_project;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class chatRoom extends Fragment {


    private String key;

    public chatRoom() {
        // Required empty public constructor
    }


    Button addmsg;
    EditText msg;
    ListView chatList;
    ArrayAdapter<String> ChatAdapter;
    ArrayList<String> ListOfmsg = new ArrayList<>();
    private String userName,roomName;
    private DatabaseReference root,root1;
    private String chatUsername,chatMsg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_chat_room, container, false);
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        addmsg = (Button) view.findViewById(R.id.Addchat);
        msg = (EditText) view.findViewById(R.id.msg);
        chatList = (ListView) view.findViewById(R.id.chatList);


        Bundle bundle = getArguments();
        userName = bundle.getString("UserName");
        roomName = bundle.getString("RoomName");

        root = FirebaseDatabase.getInstance().getReference().child(roomName);
        root1 = FirebaseDatabase.getInstance().getReference().child(userName);


        addmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> specificuserMsg = new HashMap<String,Object>();
                key = root.push().getKey();
                root.updateChildren(specificuserMsg);

                DatabaseReference messageRoot = root.child(key);
                DatabaseReference messageRoot1 = root1.child(key);
                Map<String,Object> allMsg = new HashMap<String,Object>();
                allMsg.put("Name" , userName);
                allMsg.put("Message",msg.getText().toString());

                messageRoot.updateChildren(allMsg);
                messageRoot1.updateChildren(allMsg);
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                get_Chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                get_Chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void get_Chat(DataSnapshot dataSnapshot)
    {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext())
        {
            chatMsg = (String) ((DataSnapshot)i.next()).getValue();
            chatUsername = (String) ((DataSnapshot)i.next()).getValue();
            ListOfmsg.add(chatUsername +"\uD83D\uDE0A : " + chatMsg + "\n");
        }
        if(getActivity() != null) {
            ChatAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ListOfmsg);
            chatList.setAdapter(ChatAdapter);
        }
        // if go to list and choose chat and write its crash
    }

}
