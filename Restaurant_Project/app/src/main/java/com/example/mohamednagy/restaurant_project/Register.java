package com.example.mohamednagy.restaurant_project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText userName;
    EditText password;
    EditText Email;
    EditText address;
    EditText phone;
    Button registerBtn;
    ImageView usernameTick;
    ImageView usernameFalse;
    ImageView emailTick;
    ImageView emailFalse;
    ImageView passwordTick;
    ImageView passwordFalse;
    ImageView addressTick;
    ImageView addressFalse;
    ImageView phoneTick;
    ImageView phoneFalse;
    RadioGroup rg;
    RadioButton selectedType;
    Database db;
    SQLiteDatabase sql;
    Button register;
    public static int valid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sql = openOrCreateDatabase("myDB", 0, null);
        db = new Database(sql);

        register = (Button) findViewById(R.id.Registerbtn);
        register.setOnClickListener(this);


        userName = (EditText) findViewById(R.id.userNameRegister);
        usernameTick = (ImageView) findViewById(R.id.usernameTick);
        usernameTick.setVisibility(View.INVISIBLE);
        usernameFalse = (ImageView) findViewById(R.id.usernameFalse);
        usernameFalse.setVisibility(View.INVISIBLE);

        userName.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (userName.getText().toString().length() != 0 && db.checkAvilabilty(userName.getText().toString())) {
                        usernameFalse.setVisibility(View.INVISIBLE);
                        usernameTick.setVisibility(View.VISIBLE);
                        valid++;
                    } else {
                        usernameTick.setVisibility(View.INVISIBLE);
                        usernameFalse.setVisibility(View.VISIBLE);
                        valid--;
                    }
                }
            }
        });

        password = (EditText) findViewById(R.id.passWordRegister);
        passwordTick = (ImageView) findViewById(R.id.passwordTick);
        passwordTick.setVisibility(View.INVISIBLE);
        passwordFalse = (ImageView) findViewById(R.id.passwordFalse);
        passwordFalse.setVisibility(View.INVISIBLE);

        password.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (password.getText().toString().length() != 0 && password.getText().toString().length() > 6) {
                        passwordFalse.setVisibility(View.INVISIBLE);
                        passwordTick.setVisibility(View.VISIBLE);
                        valid++;
                    } else if (password.getText().toString().length() < 6) {
                        passwordTick.setVisibility(View.INVISIBLE);
                        passwordFalse.setVisibility(View.VISIBLE);
                        valid--;
                    }
                }
            }
        });

        Email = (EditText) findViewById(R.id.EmailRegister);
        emailTick = (ImageView) findViewById(R.id.EmailTick);
        emailTick.setVisibility(View.INVISIBLE);
        emailFalse = (ImageView) findViewById(R.id.EmailFalse);
        emailFalse.setVisibility(View.INVISIBLE);

        Email.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (Email.getText().toString().matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$")) {
                        emailFalse.setVisibility(View.INVISIBLE);
                        emailTick.setVisibility(View.VISIBLE);
                        valid++;
                    } else if (Email.getText().toString().length() == 0 || !Email.getText().toString().matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$")) {
                        emailTick.setVisibility(View.INVISIBLE);
                        emailFalse.setVisibility(View.VISIBLE);
                        valid--;
                    }
                }
            }
        });

        address = (EditText) findViewById(R.id.addressRegister);
        addressTick = (ImageView) findViewById(R.id.addressTick);
        addressTick.setVisibility(View.INVISIBLE);
        addressFalse = (ImageView) findViewById(R.id.addressFalse);
        addressFalse.setVisibility(View.INVISIBLE);

        address.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (address.getText().toString().length() > 3) {
                        addressFalse.setVisibility(View.INVISIBLE);
                        addressTick.setVisibility(View.VISIBLE);
                        valid++;
                    } else {
                        addressTick.setVisibility(View.INVISIBLE);
                        addressFalse.setVisibility(View.VISIBLE);
                        valid--;
                    }
                }
            }
        });

        phone = (EditText) findViewById(R.id.phoneRegister);
        phoneTick = (ImageView) findViewById(R.id.phoneTick);
        phoneTick.setVisibility(View.INVISIBLE);
        phoneFalse = (ImageView) findViewById(R.id.phoneFalse);
        phoneFalse.setVisibility(View.INVISIBLE);

        phone.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (phone.getText().toString().length() == 11) {
                        phoneFalse.setVisibility(View.INVISIBLE);
                        phoneTick.setVisibility(View.VISIBLE);
                        valid++;
                    } else {
                        phoneTick.setVisibility(View.INVISIBLE);
                        phoneFalse.setVisibility(View.VISIBLE);
                        valid--;
                    }
                }
            }
        });

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.check(R.id.userRadio);


    }

    @Override
    public void onClick(View view) {
        selectedType = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        String userType = selectedType.getText().toString();
        if (phoneTick.isShown() && usernameTick.isShown() && passwordTick.isShown() && emailTick.isShown() && addressTick.isShown()) {
            if(db.checkAvilabilty(userName.getText().toString())) {
                db.addUser(userName.getText().toString(),password.getText().toString(),Email.getText().toString(),address.getText().toString(),phone.getText().toString(),userType);
                String userData = db.checkLogin(userName.getText().toString(),password.getText().toString());
                if (userType.equals("Admin"))
                {
                    Intent intent = new Intent(this,Admin_Activity.class);
                    intent.putExtra("Id", userData);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(this,User_Activity.class);
                    intent.putExtra("Id", userData);
                    startActivity(intent);
                }

            }
            else {
                Toast.makeText(this,"User Name Exists \uD83D\uDE1E",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "There Is an Empty or Wrong Field(s)", Toast.LENGTH_SHORT).show();
        }
    }
    public void onPause()
    {
        super.onPause();
        finish();
    }
}
