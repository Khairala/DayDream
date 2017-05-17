package com.example.mohamednagy.restaurant_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class Database {
    SQLiteDatabase sql;

    public Database(SQLiteDatabase sql) {
        this.sql = sql;
    }


    public void createTables() {
        try {

            sql.execSQL("create table if not exists users (ID INTEGER PRIMARY KEY AUTOINCREMENT,userName text not null unique" +
                    ", Email text not null, Password text not null , Address text not null , Phone text not null, type text not null)");
            sql.execSQL("create table if not exists category (ID INTEGER PRIMARY KEY AUTOINCREMENT,categoryName text unique)");
            sql.execSQL("create table if not exists food (ID INTEGER PRIMARY KEY AUTOINCREMENT,food_name text unique" +
                    ", price text,image text,categoryID INTEGER , FOREIGN KEY(categoryID) REFERENCES category(ID))");
            sql.execSQL("create table if not exists orders (ID INTEGER PRIMARY KEY AUTOINCREMENT,foodname text,price text, uID INTEGER)");
        } catch (Exception e) {
            Log.e("table", "ERRRRRRRRRRRRORRRRRR");
            e.printStackTrace();
        }
    }

    public String getType(int id)
    {
        String type = new String();
        Cursor cur = sql.rawQuery("select type from users where ID = " + id, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
               type = cur.getString(0);
            }
        }
        cur.close();
        return type;
    }


    public void addUser(String name,String password,String Email,String Address,String Phone ,String Type) {
        sql.execSQL("insert into users(userName,Email,Password,Address,Phone,type) values ('"+name+"' , '"+Email+"' , '"+password+"' , '"+Address+"' , '"+Phone+"' , '"+Type+"')");
        Log.e("user added", "added");
    }

    public ArrayList<String> getUser(int id) {
        ArrayList<String> arr = new ArrayList<String>();
        Cursor cur = sql.rawQuery("select userName,Email,Password,Address,Phone from users where ID = " + id, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
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

    public void updateUser(String userName, String Email, String Password, String Address, String Phone) {
        String query = "UPDATE users SET Email = " + "'" + Email + "'," + " Password = " + "'" + Password + "'," +
                " Address = " + "'" + Address + "'," + "Phone = " +
                "'" + Phone + "' WHERE userName = " + "'" + userName + "'";
        sql.execSQL(query);
        Log.e("update", "updaaaaaaaaaaaaaaaaaaaaaaaaaaaaated");
    }

    public String checkLogin(String userName, String Password) {
        String Id = new String();
        Cursor cur = sql.rawQuery("select ID from users where userName = " + "'" + userName + "'"
                + " and Password = " + "'" + Password + "'", null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                Id = cur.getString(0);
            }
            Log.e("found", "user FOUUUUUUUUUUUUUUUUND");
            cur.close();
            return Id;
        } else {
            return null;
        }
    }

    public void addFood(String foodName, String price, int img, String categoryName) {
        Cursor cur = sql.rawQuery("select ID from category WHERE categoryName = '" + categoryName + "'", null);
        int Id = 0;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                Log.e("ffffffffffffff", cur.getInt(0) + "");
                Id = cur.getInt(0);
            }
            sql.execSQL("insert into food(food_name,price,image,categoryID) values ('" + foodName + "' , '" + price + "', '" + img + "'," + Id + ")");
            Log.e("food added", "added");
        }

    }

    public void addCategory(String name) {
        sql.execSQL("insert into category ( categoryName) values ('" + name + "')");
        Log.e("category Found" , "Category Fouuuuuuuuund");
    }

    public ArrayList<String> getCategory() {
        ArrayList<String> arr = new ArrayList<String>();
        Cursor cur = sql.rawQuery("select categoryName from category ", null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                arr.add(cur.getString(0));
            }
            cur.close();
            return arr;
        } else {
            return null;
        }
    }


    public Boolean checkAvilabilty (String name , String tableName , String columnName) {
        Cursor cur = sql.rawQuery("select ID from "+tableName+" where "+columnName+" = '"+name+"'", null);
        if (cur.getCount() > 0) {
            return false;
        }else
        {
            return true;
        }
    }



    public ArrayList<FoodItem> getAllfood(String categoryName) {
        Cursor curX = sql.rawQuery("select ID from category where categoryName = '" + categoryName + "'", null);
        int ID = 0;
        if (curX.getCount() > 0) {
            while (curX.moveToNext()) {
                ID = curX.getInt(0);
                Log.e("Food Database", "" + ID);

            }
            ArrayList<FoodItem> arr = new ArrayList<FoodItem>();
            Cursor cur = sql.rawQuery("select food_name,price,image from food where categoryID = " + ID, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    FoodItem f = new FoodItem(cur.getString(0), cur.getString(1), cur.getInt(2));
                    Log.e("Food Database", "" + f);
                    arr.add(f);
                }
                cur.close();
                return arr;
            } else {
                return null;
            }
        }
        return null;
    }


    public void dropFoodtable() {
        String dropQuery = "DROP TABLE IF EXISTS food";
        sql.execSQL(dropQuery);
        Log.e("table", "drop allllllllll");
    }

    public void dropCategorytable() {
        String dropQuery = "DROP TABLE IF EXISTS category";
        sql.execSQL(dropQuery);
        Log.e("table", "drop allllllllll");
    }

    public void dropUsertable() {
        String dropQuery = "DROP TABLE IF EXISTS users";
        sql.execSQL(dropQuery);
        Log.e("table", "drop allllllllll");
    }

    public void dropUserOrders() {
        String dropQuery = "DROP TABLE IF EXISTS orders";
        sql.execSQL(dropQuery);
        Log.e("table", "drop allllllllll");
    }
    public void addOrder(String foodName, String price,int userId) {
        try {
            sql.execSQL("insert into orders(foodname,price,uID) values ('" + foodName + "' , '" + price + "' , "+userId+" )");
            Log.e("order added", "added");
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getUserOrder(int id) {
        Log.e("ID ISSSSSSSS", " "+id);
        ArrayList<String> arr = new ArrayList<String>();
        Cursor cur = sql.rawQuery("select foodname,price from orders where uID = " + id, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                arr.add("Food Name : "+cur.getString(0)+"       Price : "+cur.getString(1)+"$"+"  \uD83D\uDE0A \uD83D\uDE0A");
            }
        }
        cur.close();
        return arr;
    }

    public int getCategoryId(String categoryName)
    {
        int type = 0;
        Cursor cur = sql.rawQuery("select ID from category where categoryName = '" + categoryName+"'", null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                type = cur.getInt(0);
            }
        }
        cur.close();
        return type;
    }

    public void deleteCategory(String categoryName)
    {
        int categoryID = getCategoryId(categoryName);
        String delFoods = "DELETE FROM food WHERE categoryID = " + categoryID;
        String delCategory = "DELETE FROM category WHERE ID = " + categoryID;
        sql.execSQL(delFoods);
        sql.execSQL(delCategory);
    }
    public ArrayList<String> getAllfoodAdmin (){
        ArrayList<String> foodArr = new ArrayList<>();
        Cursor curX = sql.rawQuery("select food_name , price from food ", null);
        if (curX.getCount() > 0) {
            while (curX.moveToNext()) {
                foodArr.add("Name : "+curX.getString(0)+" \nPrice : "+curX.getString(1));
                Log.e("wfwe", "foooood") ;
            }
            return foodArr;
        }
        return null;
    }

    public void deleteFood(String foodName)
    {
        String[] xx = foodName.split(" ");
        String delFoods = "DELETE FROM food WHERE food_name = '" + xx[2] + "'";
        sql.execSQL(delFoods);
        Log.e("wfwe", "delete foooood") ;

    }

}
