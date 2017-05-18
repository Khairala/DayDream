package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
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

import static com.example.mohamednagy.restaurant_project.Login.userData;


/**
 * A simple {@link Fragment} subclass.
 */
public class chatRoom extends Fragment {


    private String key;

    public chatRoom() {
        // Required empty public constructor
    }

    SQLiteDatabase sql;
    Database db;
    Button addmsg;
    EditText msg;
    ListView chatList;
    ArrayAdapter<String> ChatAdapter;
    ArrayList<String> ListOfmsg = new ArrayList<>();
    private String userName,roomName;
    private DatabaseReference root;
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
        sql = getActivity().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);

        ArrayList<String> user = db.getUser(userData);

        userName = user.get(0);
        roomName = "chat";

        root = FirebaseDatabase.getInstance().getReference().child(roomName);


        addmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> specificuserMsg = new HashMap<String,Object>();
                key = root.push().getKey();
                root.updateChildren(specificuserMsg);

                DatabaseReference messageRoot = root.child(key);
                Map<String,Object> allMsg = new HashMap<String,Object>();
                allMsg.put("Name" , userName);
                allMsg.put("Message",msg.getText().toString());

                messageRoot.updateChildren(allMsg);
                msg.setText("");
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
            ListOfmsg.add("\n"+chatUsername +"\uD83D\uDE0A : \n\n" + chatMsg + "\n");
        }
        if(getActivity() != null) {
            ChatAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ListOfmsg);
            chatList.setAdapter(ChatAdapter);
        }
    }

}
