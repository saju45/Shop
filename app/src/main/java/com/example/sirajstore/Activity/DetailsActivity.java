package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.sirajstore.Adapter.ProductAdapter;
import com.example.sirajstore.Model.ProductModel;
import com.example.sirajstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    EditText search;
    ImageView mic;

    Button saveButton,closeButton;
    EditText productName,buy,sell,poriman;

    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<ProductModel> list;
    ProductAdapter adapter;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        floatingActionButton=findViewById(R.id.floatingActionButton2);
        recyclerView=findViewById(R.id.RecyclerView);
        search=findViewById(R.id.editText);
        mic=findViewById(R.id.mic);

        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());

        reference=FirebaseDatabase.getInstance().getReference("Product");
        database=FirebaseDatabase.getInstance();

        list=new ArrayList<>();
        adapter=new ProductAdapter(this,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailsActivity.this, "You click", Toast.LENGTH_SHORT).show();
                showDialog();
            }
        });

       database.getReference().child("Product").addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               list.clear();
               for(DataSnapshot snapshot1:snapshot.getChildren())
               {
                   ProductModel model=snapshot1.getValue(ProductModel.class);

                   list.add(model);
               }adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

      search.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {

              filter(s.toString());
          }
      });

       mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) !=null)
                {
                    startActivityForResult(intent,10);
                }else {
                    Toast.makeText(getApplicationContext(), "Your Device Didn't Support input Speach", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void filter(String text) {

        ArrayList<ProductModel> filteredList = new ArrayList<>();

        for (ProductModel item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {//saju
            case 10:
                if (resultCode==RESULT_OK && data!=null)
                {
                    ArrayList<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    search.setText(result.get(0));
                }
                break;
        }



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

        View view=inflater.inflate(R.layout.data,null);

        productName=view.findViewById(R.id.productName);
        buy=view.findViewById(R.id.kena);
        sell=view.findViewById(R.id.sell);
        poriman=view.findViewById(R.id.poriman);
        saveButton=view.findViewById(R.id.saveButton);
        closeButton=view.findViewById(R.id.closeButton);

        builder.setView(view);
        builder.setCancelable(false);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String producname_=productName.getText().toString();
                String buy_=buy.getText().toString();
                String sell_=sell.getText().toString();

                Toast.makeText(getApplicationContext(), "productName : "+producname_, Toast.LENGTH_SHORT).show();
            }
        });



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
                String buy_=buy.getText().toString();
                String sell_=sell.getText().toString();

                if (name.isEmpty())
                {
                    productName.setError("পণ্যের নাম দিন");
                    productName.requestFocus();
                }else if (poriman_.isEmpty())
                {
                    poriman.setError("পণ্যের পরিমান দিন ");
                    poriman.requestFocus();
                }else if (buy_.isEmpty())
                {
                    buy.setError("পণ্যের ক্রয় মূল্য দিন ");
                    buy.requestFocus();
                }else if (sell_.isEmpty())
                {
                    sell.setError("পণ্যের িক্রয় মূল্য দিন ");
                    sell.requestFocus();
                }else {

                    String postId=reference.push().getKey();
                    ProductModel model=new ProductModel();
                    // ProductModel productModel=new ProductModel(name,poriman_,buy_,sell_);

                    ProductModel productModel=new ProductModel();
                    productModel.setName(name);
                    productModel.setPoriman(poriman_);
                    productModel.setBuy(buy_);
                    productModel.setSell(sell_);
                    productModel.setPushId(postId);
                    productModel.setTime(time);

                    reference.child(postId).setValue(productModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(DetailsActivity.this, "Product Inserted successfully", Toast.LENGTH_SHORT).show();

                                productName.setText("");
                                poriman.setText("");
                                buy.setText("");
                                sell.setText("");
                            }
                        }
                    });
                }


            }
        });
    }

}