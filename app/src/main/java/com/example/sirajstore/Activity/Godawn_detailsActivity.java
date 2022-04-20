package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sirajstore.Adapter.GodawnAdapter;
import com.example.sirajstore.Adapter.ProductAdapter;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Godawn_detailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText search;
    ImageView mic;

    TextView total_money;
    Button saveButton,closeButton;
    EditText productName, dhoron,poriman,kroymullo,motdam;

    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;
    GodawnAdapter adapter;

    ArrayList<GoadawnModel> list;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_godawn_details);

        list=new ArrayList<>();
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


        recyclerView=findViewById(R.id.godawnRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
         adapter=new GodawnAdapter(this,list);
        recyclerView.setAdapter(adapter);

        reference.child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1: snapshot.getChildren())
                        {

                            GoadawnModel model=snapshot1.getValue(GoadawnModel.class);

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

        ArrayList<GoadawnModel> filteredList = new ArrayList<>();

        for (GoadawnModel item : list) {
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
        {
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

        View view=inflater.inflate(R.layout.simple_data,null);

        productName=view.findViewById(R.id.simple_data_productname);
        dhoron =view.findViewById(R.id.simple_data_dhoron);
        poriman=view.findViewById(R.id.simple_data_poriman);
        kroymullo=view.findViewById(R.id.simple_data_kroy);
        motdam=view.findViewById(R.id.simple_data_motdam);
        saveButton=view.findViewById(R.id.saveButton);
        closeButton=view.findViewById(R.id.closeButton);



        poriman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    String num1Str=s.toString();
                    if(!num1Str.isEmpty()){
                        float num1=Float.parseFloat(num1Str);
                        String num2Str=kroymullo.getText().toString();
                        if(!num2Str.isEmpty()){
                            float num2=Float.parseFloat(num2Str);
                            motdam.setText(""+(num1*num2));
                        }
                    }
                }else{
                    motdam.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kroymullo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!TextUtils.isEmpty(s)){
                    String num2Str=s.toString();
                    if(!num2Str.isEmpty()){
                        float num2=Float.parseFloat(num2Str);
                        String num1Str=poriman.getText().toString();
                        if(!num1Str.isEmpty()){
                            float num1=Float.parseFloat(num1Str);
                            motdam.setText(""+(num1*num2));
                        }
                    }
                }else{
                   motdam.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





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
                String type= dhoron.getText().toString();
                String kroy_mullo=kroymullo.getText().toString();
                String mot_dam=motdam.getText().toString();


                if (name.isEmpty())
                {
                    productName.setError("পণ্যের নাম দিন");
                    productName.requestFocus();
                }else if (poriman_.isEmpty())
                {
                    poriman.setError("পণ্যের পরিমান দিন ");
                    poriman.requestFocus();
                }else if (type.isEmpty())
                {
                    dhoron.setError("পণ্যের ক্রয় মূল্য দিন ");
                    dhoron.requestFocus();
                }else if (kroy_mullo.isEmpty())
                {
                    dhoron.setError("পণ্যের ক্রয় মূল্য দিন ");
                    dhoron.requestFocus();
                }else if (mot_dam.isEmpty())
                {
                    dhoron.setError("পণ্যের ক্রয় মূল্য দিন ");
                    dhoron.requestFocus();
                }else {
                    float porimand =Float.parseFloat(poriman_);
                    float mot =Float.parseFloat(mot_dam);
                    float kroy =Float.parseFloat(kroy_mullo);

                    String postId=reference.push().getKey();

                    GoadawnModel productModel=new GoadawnModel();
                    productModel.setName(name);
                    productModel.setPoriman(porimand);
                    productModel.setDhoron(type);
                    productModel.setPushId(postId);
                    productModel.setKroymullo(kroy);
                    productModel.setMotdam(mot);
                    productModel.setTimestamp(System.currentTimeMillis());
                    productModel.setTime(time);

                    reference.child(FirebaseAuth.getInstance().getUid()).child(postId).setValue(productModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(Godawn_detailsActivity.this, "Product Inserted successfully", Toast.LENGTH_SHORT).show();

                                productName.setText("");
                                poriman.setText("");
                                dhoron.setText("");
                                kroymullo.setText("");
                                motdam.setText("");
                            }
                        }
                    });
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.toato_money_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.total_money)
        {

            toatalDialog();
            //startActivity(new Intent(this,TotalMoneyAcitvity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    public void toatalDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.godown_total_money,null);

        total_money=view.findViewById(R.id.simple_godown_total);


        reference.child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        double TotalMoney=0;
                        for (DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            GoadawnModel model=snapshot1.getValue(GoadawnModel.class);

                            TotalMoney+=model.getMotdam();


                        }
                        total_money.setText(TotalMoney+"/="+"");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        builder.setView(view);

        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();



    }
}