package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Space2Patient extends AppCompatActivity {

    private Button profilVoyageur,patient,rateAgence;
    private ImageView a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space2_patient);

        profilVoyageur = findViewById(R.id.profilVoyageur);
        patient = findViewById(R.id.patient);
        rateAgence =findViewById(R.id.rateAgence);
        a = findViewById(R.id.imageViewa);


        profilVoyageur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space2Patient.this, DataCrud2.class));
                finish();
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space2Patient.this, MesPatientsVoyagePrix2.class));
                finish();
            }
        });

        rateAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space2Patient.this, RateAgence.class));
                finish();
            }
        });



        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Space2Patient.this, Login2.class));
                finish();
            }
        });

    }
}