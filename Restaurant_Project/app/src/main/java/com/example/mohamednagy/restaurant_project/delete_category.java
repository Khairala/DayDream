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
public class delete_category extends Fragment {

    EditText categoryName;
    Button delCategory;
    Database db;
    SQLiteDatabase sql;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        View view =  inflater.inflate(R.layout.fragment_delete_category, container, false);

        sql = getActivity().openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);
        categoryName = (EditText) view.findViewById(R.id.categoryNameinDelete);

        delCategory = (Button) view.findViewById(R.id.deletecatBtn);
        delCategory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!db.checkAvilabilty(categoryName.getText().toString() , "category" , "categoryName")&&categoryName.getText().length()>0)
                    {
                        db.deleteCategory(categoryName.getText().toString());
                        Toast.makeText(getActivity() , "Category Deleted \uD83D\uDE03" , Toast.LENGTH_SHORT).show();
                    }else if (db.checkAvilabilty(categoryName.getText().toString(), "category", "categoryName") && categoryName.getText().length() != 0)
                    {
                        Toast.makeText(getActivity() , "Category NOT FOUND \uD83D\uDE1E" , Toast.LENGTH_SHORT).show();
                    }else if(categoryName.getText().length() == 0)
                    {
                        Toast.makeText(getActivity() , "Fill DATA " , Toast.LENGTH_SHORT).show();
                    }
                }
            });


        return view;
    }

}
