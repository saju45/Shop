package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.sirajstore.R;
import com.example.sirajstore.databinding.ActivityGodownEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class GodownEditActivity extends AppCompatActivity {

    ActivityGodownEditBinding binding;
    DatabaseReference reference;
    float updatedPoriman=0f;
    float updatedKroy=0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGodownEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference= FirebaseDatabase.getInstance().getReference("godawn");


        Intent intent=getIntent();

        String pushId=intent.getStringExtra("pushId");
        String name=intent.getStringExtra("name");
        String type=intent.getStringExtra("type");
         float poriman=intent.getFloatExtra("poriman",0);
         float kroy=intent.getFloatExtra("kroy",0);


         updatedKroy=kroy;
         updatedPoriman=poriman;

         binding.simpleDataProductname.setText(name);
         binding.simpleDataDhoron.setText(type);
         binding.simpleDataPoriman.setText(poriman+"");
         binding.simpleDataKroy.setText(kroy+"");


        binding.simpleDataPoriman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!TextUtils.isEmpty(s)){
                    String num2Str=s.toString();
                    if(!num2Str.isEmpty()){
                        updatedPoriman=Float.parseFloat(num2Str);  ;
                        float totalPoriman=updatedPoriman*updatedKroy;
                        binding.simpleDataMotdam.setText(""+totalPoriman);

                    }
                }else{
                    binding.simpleDataMotdam.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      binding.simpleDataKroy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!TextUtils.isEmpty(s)){
                    String num2Str=s.toString();
                    if(!num2Str.isEmpty()){
                        updatedKroy=Float.parseFloat(num2Str);
                        float totalPoriman=updatedKroy*updatedPoriman;
                        binding.simpleDataMotdam.setText(""+totalPoriman);

                    }
                }else{
                    binding.simpleDataMotdam.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        binding.saveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String name_=binding.simpleDataProductname.getText().toString();
                 String type_=binding.simpleDataDhoron.getText().toString();
                 String poriman_=binding.simpleDataPoriman.getText().toString();
                 String kroy_=binding.simpleDataKroy.getText().toString();
                 String motdam=binding.simpleDataMotdam.getText().toString();

                 if (name_.isEmpty())
                 {
                     binding.simpleDataProductname.setError("Pleas enter product name");
                     binding.simpleDataProductname.requestFocus();
                 }else if (type_.isEmpty())
                 {
                     binding.simpleDataDhoron.setError("Please enter product type");
                     binding.simpleDataDhoron.requestFocus();
                 }else if (poriman_.isEmpty())
                 {
                     binding.simpleDataPoriman.setError("Please enter poriman");
                     binding.simpleDataPoriman.requestFocus();
                 }else if (kroy_.isEmpty())
                 {
                     binding.simpleDataKroy.setError("Please enter kroy mullo");
                     binding.simpleDataKroy.requestFocus();
                 }else if (motdam.isEmpty())
                 {
                     binding.simpleDataMotdam.setError("Please enter kroy mullo");
                     binding.simpleDataMotdam.requestFocus();
                 }else {

                     float porimanf=Float.parseFloat(poriman_);
                     float kroyf=Float.parseFloat(kroy_);
                     float mot_dam=Float.parseFloat(motdam);

                     HashMap<String,Object> hashMap=new HashMap<>();
                     hashMap.put("name",name_);
                     hashMap.put("dhoron",type_);
                     hashMap.put("poriman",porimanf);
                     hashMap.put("kroymullo",kroyf);
                     hashMap.put("motdam",mot_dam);

                     reference.child(FirebaseAuth.getInstance().getUid()).child(pushId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {


                             if (task.isSuccessful())
                             {
                                 Toast.makeText(GodownEditActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(GodownEditActivity.this,Godawn_detailsActivity.class));
                                 finish();
                             }

                         }
                     });

                 }


             }
         });


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}