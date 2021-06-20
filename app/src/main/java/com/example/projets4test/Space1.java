package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Space1 extends AppCompatActivity {

    private Button profil,rdv,patient;
    private ImageView a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space1);

        profil = findViewById(R.id.profil);
        rdv = findViewById(R.id.rdv);
        patient = findViewById(R.id.patient);
        a = findViewById(R.id.imageViewa);


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space1.this, DataCrud.class));
                finish();
            }
        });

        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space1.this, AjouterVoyage.class));
                finish();
            }
        });

/*
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space1.this, MesPatients.class));
                finish();
            }
        });

*/

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space1.this, MesPatientsVoyagePrix.class));
                finish();
            }
        });



        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space1.this, Login.class));
                finish();
            }
        });

    }
}