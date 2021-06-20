package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DataCrud extends AppCompatActivity {


    private Button insert, crud, delete;
    private ImageView z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_crud);

        insert = findViewById(R.id.insert);
        crud = findViewById(R.id.crud);



        z = findViewById(R.id.imageViewa);

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.DataCrud.this, com.example.projets4test.Space1.class));
                finish();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.DataCrud.this, com.example.projets4test.Insert.class));
                finish();
            }
        });



        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.DataCrud.this, Profile.class));
                finish();
            }
        });










    }
}