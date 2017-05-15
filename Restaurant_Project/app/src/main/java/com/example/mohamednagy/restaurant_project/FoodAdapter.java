package com.example.mohamednagy.restaurant_project;

/**
 * Created by MohamedNagy on 5/6/2017.
 */

import android.content.Context;
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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<FoodItem> foodItems;
    private Context context;

    public FoodAdapter(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_layout, null);

        ViewHolder viewHolder = new ViewHolder(itemLayout);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(this.foodItems.get(position).title);
        holder.price.setText(this.foodItems.get(position).price);
        holder.image.setImageResource(this.foodItems.get(position).imageUrl);
        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "this position number = " + position, Toast.LENGTH_SHORT).show();
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
