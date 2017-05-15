package com.example.mohamednagy.restaurant_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Database {
    SQLiteDatabase sql;
    public Database(SQLiteDatabase sql)
    {
        this.sql = sql;
    }
    public void dropTables()
    {
        String dropQuery = "DROP TABLE IF EXISTS users";
        sql.execSQL(dropQuery);
        Log.e("table" , "drop allllllllll");
    }

    public void createTables()
    {
        try {

            String userTable = "create table if not exists users (ID INTEGER PRIMARY KEY AUTOINCREMENT,userName text not null unique" +
                    ", Email text not null, Password text not null , Address text not null , Phone address not null)";
            sql.execSQL(userTable);
            Log.e("table" , "tableCreated xxxxxxxxxxxxxxxxxxxxx");
        }catch (Exception e)
        {
            Log.e("table" , "ERRRRRRRRRRRRORRRRRR");
            e.printStackTrace();
        }
    }


    public void addUser ()
    {
        sql.execSQL("insert into users(userName,Email,Password,Address,Phone) values ('kiko1111' , 'medoo1192@gmail.com' , '12345' , 'cairo' , '01144098850')");
        Log.e("user added","added");
    }

    public ArrayList<String> getUser (int id)
    {
        ArrayList<String> arr = new ArrayList<String>();
        Cursor cur = sql.rawQuery("select userName,Email,Password,Address,Phone from users where ID = "+ id ,null);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                arr.add(cur.getString(0));
                arr.add(cur.getString(1));
                arr.add(cur.getString(2));
                arr.add(cur.getString(3));
                arr.add(cur.getString(4));
            }
        }
        cur.close();
        return arr;
    }
    public void updateUser (String userName ,String Email ,String Password ,String Address ,String Phone)
    {
        String query = "UPDATE users SET Email = "+"'"+Email+"',"+" Password = "+"'"+Password+"'," +
                " Address = "+"'"+Address+"',"+ "Phone = "+
                "'"+Phone+"' WHERE userName = "+"'" +userName+"'";
        sql.execSQL(query);
        Log.e("update" , "updaaaaaaaaaaaaaaaaaaaaaaaaaaaaated");
    }
    public String checkLogin (String userName , String Password)
    {
        String Id = new String();
        Cursor cur = sql.rawQuery("select ID from users where userName = "+ "'"+userName+"'"
             + " and Password = "+ "'"+Password+"'",null);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                Id = cur.getString(0);
            }
            Log.e("found" , "user FOUUUUUUUUUUUUUUUUND");
            cur.close();
            return Id;
        }else{return null;}
    }

    public void createfoodtable()
    {
        try {
            sql.execSQL("create table if not exists food (ID INTEGER PRIMARY KEY AUTOINCREMENT,food_name text" +
                    ", price text,image text)");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void addFood(String name,String price,int img)
    {
        sql.execSQL("insert into food(food_name,price,image) values ('"+name+"' , '"+price+"', '"+img+"')");
        Log.e("food added","added");
    }

    public ArrayList<FoodItem> getAllfood()
    {
        ArrayList<FoodItem> arr = new ArrayList<FoodItem>();
        Cursor cur = sql.rawQuery("select food_name,price,image from food ",null);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                FoodItem f = new FoodItem(cur.getString(0),cur.getString(1),cur.getInt(2));
                arr.add(f);
            }
            cur.close();
            return arr;
        }else{return null;}
    }
    public void dropTablesfood()
    {
        String dropQuery = "DROP TABLE IF EXISTS food";
        sql.execSQL(dropQuery);
        Log.e("table" , "drop allllllllll");
    }
}
