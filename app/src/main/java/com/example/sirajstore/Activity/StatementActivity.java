package com.example.sirajstore.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.sirajstore.Fragments.ItemInFragment;
import com.example.sirajstore.Fragments.ItemOutFragment;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.ActivityStatementBinding;

public class StatementActivity extends AppCompatActivity {

    ActivityStatementBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStatementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ItemInFragment()).commit();

        binding.inBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ItemInFragment()).commit();
            }
        });

         binding.outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ItemOutFragment()).commit();
            }
        });



    }
}