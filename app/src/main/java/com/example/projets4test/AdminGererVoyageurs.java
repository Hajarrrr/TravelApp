package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminGererVoyageurs extends AppCompatActivity {

    private ImageView imageView;


    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdapterVoyageurs adapter;
    private List<ModelVoyageurs> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gerer_voyageurs);


        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminGererVoyageurs.this, ChefSpace.class));
                finish();
            }
        });



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyAdapterVoyageurs(this , list);
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


        db.collection("Voyageur")
                //.whereEqualTo(("nom_agence").toLowerCase(),NomAg)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            if(snapshot.getString("nom")!= null){
                                //(String id, String nom, String cin, String email, String specialite, String telephone)
                                ModelVoyageurs model = new ModelVoyageurs(snapshot.getString("id") , snapshot.getString("nom") , snapshot.getString("cin"), snapshot.getString("email") , snapshot.getString("specialite"), snapshot.getString("telephone") );
                                list.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminGererVoyageurs.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}