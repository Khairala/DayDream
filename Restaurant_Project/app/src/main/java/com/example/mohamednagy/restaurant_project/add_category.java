package com.example.mohamednagy.restaurant_project;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_category extends Fragment {
    Button addCategory;
    EditText categoryName;
    Database db;
    SQLiteDatabase sql;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container != null)
        {
            container.removeAllViewsInLayout();
        }
        View view= inflater.inflate(R.layout.fragment_add_category, container, false);
        sql = getActivity().openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);
        categoryName = (EditText) view.findViewById(R.id.categoryName);

        addCategory = (Button) view.findViewById(R.id.addcatBtn);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkAvilabilty(categoryName.getText().toString() , "category" , "categoryName")&&categoryName.getText().length()>0)
                {
                    db.addCategory(categoryName.getText().toString());
                    Toast.makeText(getActivity() , "Category ADDED \uD83D\uDE03" , Toast.LENGTH_SHORT).show();
                }else if (!db.checkAvilabilty(categoryName.getText().toString(), "category", "categoryName"))
                {
                    Toast.makeText(getActivity() , "Category is Found \uD83D\uDE1E" , Toast.LENGTH_SHORT).show();
                }else if(categoryName.getText().length() == 0)
                {
                    Toast.makeText(getActivity() , "ERROR !!!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
