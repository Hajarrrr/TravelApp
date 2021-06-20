package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MesRate extends AppCompatActivity {

    private ImageView imageView;


    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private com.example.projets4test.MyAdapterRate adapter;
    private List<ModelRate> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_rate);

        imageView = findViewById(R.id.imageView2);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.MesRate.this, com.example.projets4test.Space2Patient.class));
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new com.example.projets4test.MyAdapterRate(this , list);
        recyclerView.setAdapter(adapter);
/*
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);*/

        showData();

        EditText searchBoxbyAgence = findViewById(R.id.searchRateAgence);

        //search by agence name
        searchBoxbyAgence.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("SEARCH","Searchbox has changed to:"+ s.toString());
                //showData();
                //search  .orderBy("nom_agence", Query.Direction.ASCENDING)


                if (s.toString().isEmpty()){
                    //when the search bar is empty show everything
                    db.collection("Rate")
                            .orderBy("nom", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    list.clear();
                                    for (DocumentSnapshot snapshot : task.getResult()){
                                        if(snapshot.getString("nom")!= null){
                                            ModelRate model = new ModelRate(snapshot.getString("id") , snapshot.getString("nom") , snapshot.getString("rate"));
                                            list.add(model);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MesRate.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    //do the search whereequal
                    db.collection("Rate")
                            .whereEqualTo(("nom").toLowerCase(),s.toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    list.clear();
                                    for (DocumentSnapshot snapshot : task.getResult()){
                                        if(snapshot.getString("nom")!= null){
                                            ModelRate model = new ModelRate(snapshot.getString("id") , snapshot.getString("nom") , snapshot.getString("rate"));
                                            list.add(model);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MesRate.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    public void showData(){
        //test if agence appartient to list
        //List<String> titleList = new ArrayList<>();
    /*
        List<String> titleList = new ArrayList<>();
        db.collection("Doctor")
                //.whereEqualTo("plan_id", plan_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //list.clear();

                        for (DocumentSnapshot snapshot : task.getResult()){
                            if(snapshot.getString("nom")!= null) {
                                titleList.add(snapshot.getString("nom"));
                                Log.d("YES", snapshot.getString("id") + " => " + snapshot.getString("nom"));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.projets4test.MesRate.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

     */
                /*
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> titleList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                titleList.add(document.get("nom").toString());
                                Log.d("YES", document.getId() + " => " + document.get("name"));

                            }
                        } else {
                            Log.d("NO", "Error getting documents: ");
                        }
                    }
                });

                 */
        /*db.collection("Rate")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            if(snapshot.getString("nom")!= null) {
                                com.example.projets4test.ModelRate model = new com.example.projets4test.ModelRate(snapshot.getString("id"), snapshot.getString("nom"), snapshot.getString("rate"));
                                list.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.projets4test.MesRate.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        */
        //jointures ??
        db.collection("Rate")
                .orderBy("rate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            if(snapshot.getString("nom")!= null) {
                                com.example.projets4test.ModelRate model = new com.example.projets4test.ModelRate(snapshot.getString("id"), snapshot.getString("nom"), snapshot.getString("rate"));
                                list.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.projets4test.MesRate.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}