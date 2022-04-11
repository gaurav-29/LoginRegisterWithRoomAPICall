package com.example.practicaltest.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.practicaltest.Activities.LoginActivity;
import com.example.practicaltest.Activities.MainActivity;
import com.example.practicaltest.R;
import com.example.practicaltest.RoomDatabase.User;
import com.example.practicaltest.RoomDatabase.UserDao;
import com.example.practicaltest.RoomDatabase.UserDataBase;
import com.example.practicaltest.Utils.GlobalVariableClass;
import com.example.practicaltest.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    UserDao db;
    UserDataBase dataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        String currentUserEmail2 = GlobalVariableClass.currentUserEmail;

        dataBase = Room.databaseBuilder(getActivity(), UserDataBase.class,"mi-database.db")
                .allowMainThreadQueries().build();
        db = dataBase.getUserDao();

        User user = db.getCurrentUser(currentUserEmail2);
        if(user != null){
            binding.tvFirstName.setText(user.getFirstName());
            binding.tvLastName.setText(user.getLastName());
            binding.tvEmail.setText(user.getEmail());
            binding.tvPhone.setText(user.getPhone());
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }
}