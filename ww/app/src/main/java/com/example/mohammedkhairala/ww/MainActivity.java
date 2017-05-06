package com.example.mohammedkhairala.ww;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("tab1");
        arr.add("tab2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) view;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fr = fm.beginTransaction();
        if(txt.getText().toString()=="tab1")
        {
            Toast.makeText(getBaseContext() , "tst111" , Toast.LENGTH_LONG).show();
            tst1 x= new tst1();
            fr.replace(R.id.content,x);
            fr.commit();
        }else if (txt.getText().toString()=="tab2")
        {
            Toast.makeText(getBaseContext() , "tst222" , Toast.LENGTH_LONG).show();
            tst2 x =new tst2();
            fr.replace(R.id.content,x);
            fr.commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
