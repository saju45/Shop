package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sirajstore.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {


    DatabaseReference database;
    DrawerLayout drawerLayout;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;
    ActionBarDrawerToggle toggle;

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

        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.naview);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.dokan)
                {
                    Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        cardperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,StatementActivity.class));
            }
        });

        cardwidgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,LendanActivity.class));
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {


            return true;

        }

        return super.onOptionsItemSelected(item);
    }



}