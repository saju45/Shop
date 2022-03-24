package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sirajstore.Model.GoadawnModel;
import com.example.sirajstore.Model.ProductModel;
import com.example.sirajstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Godawn_detailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText search;
    ImageView mic;

    Button saveButton,closeButton;
    EditText productName,buy,poriman;

    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_godawn_details);

        floatingActionButton=findViewById(R.id.floatingActionButton3);
        search=findViewById(R.id.editText);
        mic=findViewById(R.id.mic);

        reference=FirebaseDatabase.getInstance().getReference("godawn");
        database=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

      /*  reference.child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1: snapshot.getChildren())
                        {

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
    }
    public void showDialog(){

        AlertDialog.Builder builder;

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            builder=new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else {
            builder=new AlertDialog.Builder(this);
        }

        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.simple_data,null);

        productName=view.findViewById(R.id.productName);
        buy=view.findViewById(R.id.kena);
        poriman=view.findViewById(R.id.poriman);
        saveButton=view.findViewById(R.id.saveButton);
        closeButton=view.findViewById(R.id.closeButton);

        builder.setView(view);
        builder.setCancelable(false);

        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=productName.getText().toString();
                String poriman_=poriman.getText().toString();
                 int buy_=Integer.parseInt(buy.getText().toString());

                if (name.isEmpty())
                {
                    productName.setError("পণ্যের নাম দিন");
                    productName.requestFocus();
                }else if (poriman_.isEmpty())
                {
                    poriman.setError("পণ্যের পরিমান দিন ");
                    poriman.requestFocus();
                }else if (buy.getText().toString().length()>0)
                {
                    buy.setError("পণ্যের ক্রয় মূল্য দিন ");
                    buy.requestFocus();
                }else {

                    String postId=reference.push().getKey();
                    ProductModel model=new ProductModel();
                    // ProductModel productModel=new ProductModel(name,poriman_,buy_,sell_);

                    GoadawnModel productModel=new GoadawnModel();
                    productModel.setName(name);
                    productModel.setPoriman(poriman_);
                    productModel.setBuy(buy_);
                    productModel.setPushId(postId);
                    productModel.setTime(time);

                    reference.child(FirebaseAuth.getInstance().getUid()).child(postId).setValue(productModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(Godawn_detailsActivity.this, "Product Inserted successfully", Toast.LENGTH_SHORT).show();

                                productName.setText("");
                                poriman.setText("");
                                buy.setText("");
                            }
                        }
                    });
                }


            }
        });
    }
}