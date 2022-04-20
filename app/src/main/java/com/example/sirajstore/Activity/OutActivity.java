package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.ActivityOutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class OutActivity extends AppCompatActivity {

    ActivityOutBinding binding;
    String pushId;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    DatabaseReference reference;
    DatabaseReference databaseReference;
    FirebaseDatabase database;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());


        reference= FirebaseDatabase.getInstance().getReference("godawn");
        databaseReference=FirebaseDatabase.getInstance().getReference("Out");
        database=FirebaseDatabase.getInstance();

        Intent intent=getIntent();
        pushId=intent.getStringExtra("pushId");
        String productName=intent.getStringExtra("name");
        float productPoriman=intent.getFloatExtra("poriman",0);
        float product_kroy=intent.getFloatExtra("kroy_mullo",0);
        String productDhoron=intent.getStringExtra("type");

        binding.productName.setText(productName);
        binding.poriman.setText(productPoriman+"");
        binding.productType.setText(productDhoron);
        binding.kroyMullo.setText(product_kroy+"");


        binding.poriman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!TextUtils.isEmpty(s)){
                    String numberSTr=s.toString();
                    if (!numberSTr.isEmpty()){
                       float num1=Float.parseFloat(numberSTr);
                        String number2Str=binding.itemIn.getText().toString();
                        if (!number2Str.isEmpty()){
                            float num2=Float.parseFloat(number2Str);
                            binding.motPoriman.setText(""+(num1-num2));
                        }

                    }
                }else {
                    binding.motPoriman.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.itemIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!TextUtils.isEmpty(s)){
                    String num2Str=s.toString();
                    if(!num2Str.isEmpty()){
                        float num2=Float.parseFloat(num2Str);
                        float totalPoriman=productPoriman-num2;
                        binding.motPoriman.setText(""+totalPoriman);
                        binding.motdam.setText(""+(totalPoriman*product_kroy));

                    }
                }else{
                    binding.motPoriman.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=binding.productName.getText().toString();
                String poriman=binding.poriman.getText().toString();
                String type=binding.productType.getText().toString();
                String motporiman=binding.motPoriman.getText().toString();
                String item_in=binding.itemIn.getText().toString();
                String mot_dam=binding.motdam.getText().toString();

                if (name.isEmpty())
                {
                    binding.productName.setError("Enter Product name");
                    binding.productName.requestFocus();
                }
                else if (type.isEmpty())
                {
                    binding.productType.setError("Enter product type");
                    binding.productType.requestFocus();
                }else if (poriman.isEmpty())
                {
                    binding.poriman.getText().toString();
                    binding.poriman.requestFocus();
                }else if (item_in.isEmpty()){

                    binding.itemIn.setError("Enter exit number");
                    binding.itemIn.requestFocus();

                }

                else if (motporiman.isEmpty())
                {
                    binding.motPoriman.setError("Enter total product");
                    binding.motPoriman.requestFocus();
                }else {

                   float porimanInt=Float.parseFloat(poriman);
                   float mot_porimanInt=Float.parseFloat(motporiman);
                    float out_=Float.parseFloat(item_in);
                    float motdam=Float.parseFloat(mot_dam);

                    String pushKey=databaseReference.push().getKey();

                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("name",name);
                    hashMap.put("poriman",mot_porimanInt);
                    hashMap.put("dhoron",type);
                    hashMap.put("pushId",pushId);
                    hashMap.put("motdam",motdam);
                    hashMap.put("time",time);

                    GoadawnModel model=new GoadawnModel();
                    model.setDhoron(type);
                    model.setName(name);
                    model.setPoriman(out_);
                    model.setPushId(pushKey);
                    model.setTime(time);


                    reference.child(FirebaseAuth.getInstance().getUid())
                            .child(pushId)
                            .updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {

                                        database.getReference().child("Out")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child(pushKey)
                                                .setValue(model);
                                        Toast.makeText(OutActivity.this, "Update is successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(OutActivity.this,Godawn_detailsActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            }
        });

    }
}