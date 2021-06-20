package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MesPatientsVoyagePrix extends AppCompatActivity {


    private ImageView imageView;


    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdapterVoyagePrix adapter;
    private List<ModelVoyagePrix> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_patients_voyageprix);


        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MesPatientsVoyagePrix.this, Space1.class));
                finish();
            }
        });



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyAdapterVoyagePrix(this , list);
        recyclerView.setAdapter(adapter);
/*
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);*/



        showData();

    }


    public void showData(){

        //get the current user
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String NomAg = current_user.getEmail().substring(0, current_user.getEmail().lastIndexOf("@"));


        db.collection("Voyages")
                .whereEqualTo(("nom_agence").toLowerCase(),NomAg)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            if(snapshot.getString("nom_agence")!= null){
                                ModelVoyagePrix model = new ModelVoyagePrix(snapshot.getString("id") , snapshot.getString("nom_agence") , snapshot.getString("prix"), snapshot.getString("destination") , snapshot.getString("periode"), snapshot.getString("nombre_places") , snapshot.getString("description"));
                                list.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MesPatientsVoyagePrix.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}