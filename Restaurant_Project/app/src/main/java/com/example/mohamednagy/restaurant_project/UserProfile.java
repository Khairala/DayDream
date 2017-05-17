package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment {
    SQLiteDatabase sql;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        if(container != null)
        {
            container.removeAllViewsInLayout();
        }
        final EditText name = (EditText) view.findViewById(R.id.userNametxt);
        final EditText email = (EditText) view.findViewById(R.id.Emailtxt);
        final EditText password = (EditText) view.findViewById(R.id.passwordtxt);
        final EditText address = (EditText) view.findViewById(R.id.addresstxt);
        final EditText phone = (EditText) view.findViewById(R.id.phonetxt);
        Button save = (Button) view.findViewById(R.id.saveButton);

        sql = getActivity().openOrCreateDatabase("myDB" ,0,null);
        final Database db = new Database(sql);
       // db.dropTables();
        //db.createTables();
       // db.addUser();
        Bundle extras = getActivity().getIntent().getExtras();
        //Log.e("GGGGGGGGGGGGGGGGGGGgg",extras.getString("Id"));
        ArrayList<String> arr = db.getUser(Integer.parseInt(extras.getString("Id")));
        name.setText(arr.get(0));
        email.setText(arr.get(1));
        password.setText(arr.get(2));
        address.setText(arr.get(3));
        phone.setText(arr.get(4));
        name.setEnabled(false);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(password.getText().toString().length() > 6 && email.getText().toString().matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$") && address.getText().toString().length() > 3 && phone.getText().toString().length() == 11)
                {
                    db.updateUser(name.getText().toString(),email.getText().toString(),password.getText().toString(),address.getText().toString(),phone.getText().toString());
                    Toast.makeText(view.getContext() , "DATA UPDATED \uD83D\uDE0A" , Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(view.getContext() , "Wrong Data \uD83D\uDE1E" ,Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

}
