package com.example.practicaltest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etConfirm;
    Button btnRegister;
    TextView tvSignIn;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);

        userDao = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db")
                .allowMainThreadQueries().build().getUserDao();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confPassword = etConfirm.getText().toString().trim();

                if(password.equals(confPassword)){
                    User user = new User(name, password, email);
                    long result = userDao.insert(user); // Insert query returns long rowId. If sucessful, returns >0 else returns -1.
                    Log.d("RESULT",String.valueOf(result));
                    if(result>0) {
                        Intent l = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(l);
                    }else{
                        Toast.makeText(RegisterActivity.this,"Data not inserted successfully",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Password and confirm password is not matching",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}