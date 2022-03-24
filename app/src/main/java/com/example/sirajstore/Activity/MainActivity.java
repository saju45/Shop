package com.example.sirajstore.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.example.sirajstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {


    DatabaseReference database;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    CardView cardhome,cardchat,cardperson,cardwidgets,cardsetting,cardlogout;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardhome=findViewById(R.id.cardHome);
        cardchat=findViewById(R.id.cardChat);
        cardperson=findViewById(R.id.cardProfile);
        cardwidgets=findViewById(R.id.cardWidgets);
        cardsetting=findViewById(R.id.cardSetting);
        cardlogout=findViewById(R.id.cardLogout);

        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        time=simpleDateFormat.format(calendar.getTime());

        database=FirebaseDatabase.getInstance().getReference("Product");


        cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
                finish();
            }
        });
        cardhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DetailsActivity.class));
            }
        });

        cardchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Godawn_detailsActivity.class));
            }
        });


    }

}