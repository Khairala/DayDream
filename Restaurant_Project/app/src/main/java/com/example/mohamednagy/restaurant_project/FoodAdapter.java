package com.example.mohamednagy.restaurant_project;

/**
 * Created by MohamedNagy on 5/6/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.mohamednagy.restaurant_project.Login.userData;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<FoodItem> foodItems;
    private Context context;
    SQLiteDatabase sql;
    Database db;
    TextView HolderTxt ;


    public FoodAdapter(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_layout, null);

        ViewHolder viewHolder = new ViewHolder(itemLayout);
        context = parent.getContext();
        sql = itemLayout.getContext().openOrCreateDatabase("myDB",0,null);
        db = new Database(sql);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(this.foodItems.get(position).title);
        holder.price.setText(this.foodItems.get(position).price);
        holder.image.setImageResource(this.foodItems.get(position).imageUrl);
        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "this position number = " + position, Toast.LENGTH_SHORT).show();
                db.addOrder(foodItems.get(position).title,foodItems.get(position).price, Integer.parseInt(userData));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue(position+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView image;
        public Button request;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.foodtitle);
            price = (TextView) itemView.findViewById(R.id.foodprice);
            image = (ImageView) itemView.findViewById(R.id.foodImage);
            request = (Button) itemView.findViewById(R.id.request);

        }
    }
}
