package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sirajstore.Fragments.DashBoardFragment;
import com.example.sirajstore.Fragments.ExpenseFragment;
import com.example.sirajstore.Fragments.IncomeFragment;
import com.example.sirajstore.R;

import com.example.sirajstore.databinding.ActivityLendanBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LendanActivity extends AppCompatActivity {

    ActivityLendanBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLendanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.lendan_frame,new DashBoardFragment()).commit();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {

                    case R.id.dashboard:

                        getSupportFragmentManager().beginTransaction().replace(R.id.lendan_frame,new DashBoardFragment()).commit();
                        return true;

                    case R.id.income:
                        getSupportFragmentManager().beginTransaction().replace(R.id.lendan_frame,new IncomeFragment()).commit();
                       return true;
                    case R.id.expense:
                        getSupportFragmentManager().beginTransaction().replace(R.id.lendan_frame,new ExpenseFragment()).commit();
                      return true;

                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.lendan_frame,new DashBoardFragment()).commit();
                        return true;

                }

            }
        });
    }
}