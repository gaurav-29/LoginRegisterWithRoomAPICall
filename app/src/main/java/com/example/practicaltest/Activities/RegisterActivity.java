package com.example.practicaltest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    RegisterActivity context = RegisterActivity.this;
    EditText etFirstName, etLastName, etEmail, etPhone, etPassword, etConfirm;
    Button btnRegister;
    TextView tvSignIn;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);

        userDao = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db")
                .allowMainThreadQueries().build().getUserDao();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etFirstName.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(context, "Please enter first name", Toast.LENGTH_SHORT).show();
                else if (etLastName.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(context, "Please enter last name", Toast.LENGTH_SHORT).show();
                else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches() ||
                        etEmail.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show();
                else if (etPhone.getText().toString().trim().equalsIgnoreCase("") ||
                        (etPhone.getText().toString().trim().length() < 10))
                    Toast.makeText(context, "Phone number must be at least 10 digits.", Toast.LENGTH_SHORT).show();
                else if (etPassword.getText().toString().trim().equalsIgnoreCase("") ||
                        (etPassword.getText().toString().trim().length() < 6))
                    Toast.makeText(context, "The password must be at least 6 characters/numbers.", Toast.LENGTH_SHORT).show();
                else if (etConfirm.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(context, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                else if (!etConfirm.getText().toString().trim()
                        .equalsIgnoreCase(etPassword.getText().toString().trim()))
                    Toast.makeText(context, "Password not matched", Toast.LENGTH_SHORT).show();
                else
                     register();
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }

    private void register() {

        String firstname = etFirstName.getText().toString().trim();
        String lastname = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confPassword = etConfirm.getText().toString().trim();

        if (password.equals(confPassword)) {
            User user = new User(firstname, lastname, email, phone, password);
            long result = userDao.insert(user); // Insert query returns long rowId. If sucessful, returns >0 else returns -1.
            Log.d("RESULT", String.valueOf(result));
            if (result > 0) {
                Intent l = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(l);
            } else {
                Toast.makeText(RegisterActivity.this, "Data not inserted successfully", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Password and confirm password is not matching", Toast.LENGTH_LONG).show();
        }
    }
}