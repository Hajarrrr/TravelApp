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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register1 extends AppCompatActivity {

    private EditText  email,password;
    private TextView login;
    private Button create;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Register1.this, Login.class));
                finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();

        create = findViewById(R.id.insc);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();


            }
        });
    }

    private void createUser(){
        String mEmail = email.getText() .toString();
        String mPassword = password.getText() .toString();

        if(!mEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){

            if(!mPassword.isEmpty()){

                mAuth.createUserWithEmailAndPassword(mEmail,mPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(com.example.projets4test.Register1.this,"Register succesfully !!!", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Register.this, Login.class));
                                //finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.projets4test.Register1.this,"Registration Error !", Toast.LENGTH_SHORT).show();

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