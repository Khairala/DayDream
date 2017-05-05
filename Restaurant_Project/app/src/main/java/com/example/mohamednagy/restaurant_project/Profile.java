package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    SQLiteDatabase sql;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        if(container != null)
        {
            container.removeAllViewsInLayout();
        }
        EditText name = (EditText) view.findViewById(R.id.userNametxt);
        EditText email = (EditText) view.findViewById(R.id.Emailtxt);
        EditText password = (EditText) view.findViewById(R.id.passwordtxt);
        EditText address = (EditText) view.findViewById(R.id.addresstxt);
        EditText phone = (EditText) view.findViewById(R.id.phonetxt);

        sql = getActivity().openOrCreateDatabase("myDB" ,0,null);
        Database db = new Database(sql);
        db.dropTables();
        db.createTables();
        db.addUser();
        ArrayList<String> arr = new ArrayList<String>();
        arr = db.getUser();
        name.setText(arr.get(0));
        email.setText(arr.get(1));
        password.setText(arr.get(2));
        address.setText(arr.get(3));
        phone.setText(arr.get(4));

        name.setEnabled(false);
        return view;
    }

}
