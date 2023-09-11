package com.example.login_register_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName=findViewById(R.id.fullname);
        mEmail=findViewById(R.id.emailET);
        mPassword=findViewById(R.id.passwordET);
      mPhone=findViewById(R.id.phoneET);
        mRegisterBtn = findViewById(R.id.loginBtn);
       mLoginBtn=findViewById(R.id.CreateText);
        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        if(fAuth.getCurrentUser()!=null){

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    mRegisterBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email=mEmail.getText().toString().trim();
            String password=mPassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is Required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required");
                return;
            }
            if(password.length()<6){

                mPassword.setError("Password must be >=6 Characters");
                return;
            }
        progressBar.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else {
                        Toast.makeText(Register.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    });
mLoginBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }
}