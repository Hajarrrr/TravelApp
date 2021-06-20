package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DataCrud2 extends AppCompatActivity {


    private Button insert, crud, delete;
    private ImageView z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_crud2);

        insert = findViewById(R.id.insert);
        crud = findViewById(R.id.crud);



        z = findViewById(R.id.imageViewa);

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataCrud2.this, Space2Patient.class));
                finish();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataCrud2.this, Insert2.class));
                finish();
            }
        });



        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataCrud2.this, Profile2.class));
                finish();
            }
        });










    }
}