package com.example.practicaltest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicaltest.R;
import com.example.practicaltest.RoomDatabase.User;
import com.example.practicaltest.RoomDatabase.UserDao;
import com.example.practicaltest.RoomDatabase.UserDataBase;
import com.example.practicaltest.Utils.GlobalVariableClass;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    LoginActivity context = LoginActivity.this;
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

        dataBase = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db")
                .allowMainThreadQueries().build();
        db = dataBase.getUserDao();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches() ||
                        etEmail.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
                else if (etPassword.getText().toString().trim().equalsIgnoreCase("") ||
                        (etPassword.getText().toString().trim().length() < 6))
                    Toast.makeText(context, "The password must be at least 6 characters/numbers.", Toast.LENGTH_SHORT).show();
                else
                    login();
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

    private void login() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        User user = db.getUser(email, password);
        if (user != null) {
            GlobalVariableClass.currentUserEmail = email;
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Not Registered. Please click on Sign Up to register.", Toast.LENGTH_LONG).show();
        }
    }
}