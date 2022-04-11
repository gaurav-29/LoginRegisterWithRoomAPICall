package com.example.practicaltest.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.practicaltest.Fragments.PostFragment;
import com.example.practicaltest.Fragments.ProfileFragment;
import com.example.practicaltest.R;
import com.example.practicaltest.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainActivity context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(context, R.layout.activity_main);



        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new PostFragment();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_post:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment fragment = new PostFragment();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        return true;
                    case R.id.navigation_profile:
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        Fragment fragment2 = new ProfileFragment();
                        fragmentManager2.beginTransaction().replace(R.id.container, fragment2).commit();
                        return true;
                }
                return false;
            }
        });
    }
}