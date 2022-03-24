package com.example.sirajstore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.sirajstore.Model.User;
import com.example.sirajstore.R;
import com.example.sirajstore.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         getSupportActionBar().hide();


            dialog=new ProgressDialog(this);
            dialog.setTitle("Create Account");
            dialog.setMessage("please wait..");

            auth=FirebaseAuth.getInstance();
            database=FirebaseDatabase.getInstance();

            binding.LoginTExt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    finish();
                }
            });

            binding.signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email=binding.email.getText().toString();
                    String password=binding.password.getText().toString();

                    if (email.isEmpty()){
                        binding.email.setError("please enter your email");
                        binding.email.requestFocus();
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        binding.email.setError("Please enter valid email");
                        binding.email.requestFocus();
                    }else if (password.isEmpty())
                    {
                        binding.password.setError("enter your password");
                        binding.password.requestFocus();
                    }else {
                        dialog.show();
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialog.dismiss();
                                if (task.isSuccessful())
                                {
                                    User user=new User(email,password);

                                    database.getReference().child("user").child(auth.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(getApplicationContext(), "Data is saved", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                                            finish();
                                        }
                                    });

                                }
                            }
                        });

                    }


                }
            });


    }
}