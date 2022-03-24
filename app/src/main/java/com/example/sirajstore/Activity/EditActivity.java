package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sirajstore.Model.ProductModel;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.ActivityEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;
    Intent intent;
    ProgressDialog dialog;
    FirebaseDatabase database;
    String pushKey;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());

        database=FirebaseDatabase.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setTitle("Updating your data");
        dialog.setMessage("Please wait...");

        intent=getIntent();
       String productName=intent.getStringExtra("productName");
       String poriman=intent.getStringExtra("poriman");
       String buy=intent.getStringExtra("buy");
       String sell=intent.getStringExtra("sell");
       pushKey=intent.getStringExtra("key");

       binding.productName.setText(productName);
       binding.poriman.setText(poriman);
       binding.kena.setText(buy);
       binding.sell.setText(sell);

       binding.closeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(EditActivity.this,DetailsActivity.class));
               finish();
           }
       });

       binding.saveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String product=binding.productName.getText().toString();
               String product_poriman=binding.poriman.getText().toString();
               String product_buy=binding.kena.getText().toString();
               String product_sell=binding.sell.getText().toString();

               if (product.isEmpty())
               {
                   binding.productName.setError("পণ্যের নাম দিন");
                   binding.productName.requestFocus();
               }else if (product_poriman.isEmpty())
               {
                   binding.poriman.setError("পণ্যের পরিমান দিন ");
                   binding.poriman.requestFocus();
               }else if (product_buy.isEmpty())
               {
                   binding.kena.setError("পণ্যের ক্রয় মূল্য দিন ");
                   binding.kena.requestFocus();
               }else if (product_sell.isEmpty())
               {
                   binding.sell.setError("পণ্যের িক্রয় মূল্য দিন ");
                   binding.sell.requestFocus();
               }else{

                   dialog.show();

                   HashMap<String,Object> map=new HashMap<>();
                   map.put("name",product);
                   map.put("poriman",product_poriman);
                   map.put("buy",product_buy);
                   map.put("sell",product_sell);
                   map.put("time",time);

                   database.getReference().child("Product")
                           .child(pushKey)
                           .updateChildren(map)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           dialog.dismiss();
                           if (task.isSuccessful())
                           {
                               Toast.makeText(EditActivity.this, "data updated", Toast.LENGTH_SHORT).show();

                               binding.productName.setText("");
                               binding.poriman.setText("");
                               binding.kena.setText("");
                               binding.sell.setText("");
                           }
                       }
                   });

               }

           }
       });



    }
}