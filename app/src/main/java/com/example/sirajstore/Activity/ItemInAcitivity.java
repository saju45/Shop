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

import com.example.sirajstore.databinding.ActivityItemInAcitivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class ItemInAcitivity extends AppCompatActivity {

    ActivityItemInAcitivityBinding binding;
    String pushId;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    DatabaseReference reference;
    DatabaseReference databaseReference;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityItemInAcitivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());


        reference= FirebaseDatabase.getInstance().getReference("godawn");
        databaseReference=FirebaseDatabase.getInstance().getReference("In");


        Intent intent=getIntent();
        String productName=intent.getStringExtra("name");
       float product_poriman=intent.getFloatExtra("poriman",0);
        float product_mullo=intent.getFloatExtra("kroy_mullo",0);
        String product_type=intent.getStringExtra("type");
        pushId=intent.getStringExtra("pushId");

        binding.productName.setText(productName);
        binding.kroyMullo.setText(product_mullo+"");
        binding.poriman.setText(product_poriman+"");
        binding.productType.setText(product_type);


        binding.itemIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!TextUtils.isEmpty(s)){
                    String num2Str=s.toString();
                    if(!num2Str.isEmpty()){
                       float num2=Float.parseFloat(num2Str);  ;
                        float totalPoriman=num2+product_poriman;
                        binding.motPoriman.setText(""+totalPoriman);
                        binding.motdam.setText(""+(totalPoriman*product_mullo));

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
                String kroy_mullo=binding.kroyMullo.getText().toString();
                String motporiman=binding.motPoriman.getText().toString();
                String mot_dam=binding.motdam.getText().toString();
                String in_item=binding.itemIn.getText().toString();
                String type=binding.productType.getText().toString();

                if (name.isEmpty())
                {
                    binding.productName.setError("Enter Product name");
                    binding.productName.requestFocus();
                }
                else if (kroy_mullo.isEmpty())
                {
                    binding.kroyMullo.setError("Enter product kroy_mullo");
                    binding.kroyMullo.requestFocus();
                }else if (poriman.isEmpty())
                {
                    binding.poriman.getText().toString();
                    binding.poriman.requestFocus();
                }
                else if (in_item.isEmpty()){

                    binding.itemIn.setError("please enter in item");
                    binding.itemIn.requestFocus();

                }
                else if (motporiman.isEmpty())
                {
                    binding.motPoriman.setError("Enter total product");
                    binding.motPoriman.requestFocus();
                } else if (mot_dam.isEmpty())
                {
                    binding.motdam.setError("Enter total product rate");
                    binding.motdam.requestFocus();
                }else{

                  float porimanInt=Float.parseFloat(poriman);
                   float mot_porimanInt=Float.parseFloat(motporiman);
                    float mot_damInt=Float.parseFloat(mot_dam);
                    float in=Float.parseFloat(in_item);
                    float kroy=Float.parseFloat(kroy_mullo);

                    String pushKey=databaseReference.push().getKey();

                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("name",name);
                    hashMap.put("poriman",mot_porimanInt);
                    hashMap.put("kroymullo",kroy);
                    hashMap.put("pushId",pushId);
                    hashMap.put("time",time);
                    hashMap.put("motdam",mot_damInt);

                    GoadawnModel model=new GoadawnModel();
                    model.setDhoron(type);
                    model.setName(name);
                    model.setPoriman(in);
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

                                             databaseReference
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child(pushKey)
                                                .setValue(model);
                                        Toast.makeText(ItemInAcitivity.this, "Update is successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ItemInAcitivity.this,Godawn_detailsActivity.class));
                                        finish();
                                    }
                                }
                       });
                }

            }
        });

    }
}