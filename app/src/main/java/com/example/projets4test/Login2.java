package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {

    private EditText email,password;
    private TextView account;
    private Button log;

    private FirebaseAuth mAuth;

    //2:voyageur
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        log = findViewById(R.id.create);
        mAuth = FirebaseAuth.getInstance();

        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Login2.this, Register2.class));
                finish();

            }
        });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();


            }
        });



    }

    private void LoginUser(){
        String mEmail = email.getText() .toString();
        String mPassword = password.getText() .toString();

        if(!mEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){

            if(!mPassword.isEmpty()){

                mAuth.signInWithEmailAndPassword(mEmail,mPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(com.example.projets4test.Login2.this,"Login succesfully !!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(com.example.projets4test.Login2.this, Space2Patient.class));
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.projets4test.Login2.this,"Login Error !!!", Toast.LENGTH_SHORT).show();

                    }
                });


            }else{
                password.setError("Empty field is not allowed");
            }
        }else if(mEmail.isEmpty()){
            email.setError("Empty field is not allowed");

        }else{
            email.setError("Please enter correct email");

        }




    }

}