package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChefSpace extends AppCompatActivity {
    private Button profil, rdv;
    private ImageView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_space);

        profil = findViewById(R.id.GererAgences);
        rdv = findViewById(R.id.GererVoyageurs);
        a = findViewById(R.id.imageViewa);


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.ChefSpace.this, AdminGererAgences.class));
                finish();
            }
        });

        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.ChefSpace.this, AdminGererVoyageurs.class));
                finish();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.ChefSpace.this, Login1.class));
                finish();
            }
        });
    }
}