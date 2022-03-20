package com.example.practicaltest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicaltest.R;
import com.example.practicaltest.RoomDatabase.User;
import com.example.practicaltest.RoomDatabase.UserDao;
import com.example.practicaltest.RoomDatabase.UserDataBase;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserDao db;
    UserDataBase dataBase;
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        dataBase = Room.databaseBuilder(this, UserDataBase.class,"mi-database.db")
                .allowMainThreadQueries().build();
        db = dataBase.getUserDao();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = etEmail.getText().toString().trim();
               String password = etPassword.getText().toString().trim();

              User user = db.getUser(email, password);
                if(user != null){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
               else{
                   Toast.makeText(LoginActivity.this, "Not Registered",Toast.LENGTH_LONG).show();
               }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(r);
            }
        });
    }
}