package com.example.mohamednagy.restaurant_project;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase sql;
    EditText userName;
    EditText passWord;
    Database db;
    Button login;
    Button register;
    public static String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.registerFromLogin);

        sql = openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);
        //db.dropUsertable();
       db.createTables();
      /*  db.dropFoodtable();
        db.dropUserOrders();
        db.dropCategorytable();
        db.createTables();
*/
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if(v == login) {
            Log.e("LLLLLLLLLL","Login");
            if (userName.getText().length() > 0 && passWord.getText().length() > 0)
            {
                userData = db.checkLogin(userName.getText().toString(), passWord.getText().toString());
            }else if (userName.getText().length() == 0 || passWord.getText().length() == 0 || userData == null)
            {
                Toast.makeText(this , "FILL DATA \uD83D\uDE1E" , Toast.LENGTH_SHORT).show();
            }
            if (userData != null) {
                String Type = db.getType(Integer.parseInt(userData));
                Log.e("LLLLLLLLLL",Type);
                if( Type.equals("User")) {
                    Intent intent = new Intent(getBaseContext(), User_Activity.class);
                    Log.e("usrID ====>","   "+userData);
                    intent.putExtra("Id", userData);
                    startActivity(intent);
                }
                else if(Type.equals("Admin")){
                    Intent intent = new Intent(getBaseContext(), Admin_Activity.class);
                    intent.putExtra("Id", userData);
                    startActivity(intent);
                }
            }else
            {
                Toast.makeText(this , "Wrong Account \uD83D\uDE1E" , Toast.LENGTH_LONG).show();
            }
        }else if(v == register)
        {
            Intent intent = new Intent(getBaseContext(), Register.class);
            startActivity(intent);
        }

    }




}

