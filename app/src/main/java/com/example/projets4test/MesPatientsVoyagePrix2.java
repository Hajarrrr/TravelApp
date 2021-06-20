package com.example.projets4test;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MesPatientsVoyagePrix2 extends AppCompatActivity {


    private ImageView imageView;


    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private MyAdapterVoyagePrix2 adapter;
    private List<ModelVoyagePrix> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_patients_voyageprix2);


        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MesPatientsVoyagePrix2.this, Space2Patient.class));
                finish();
            }
        });



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyAdapterVoyagePrix2(this , list);
        recyclerView.setAdapter(adapter);
/*
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);*/
        showData();


        //added newsearch
        EditText searchBoxbyAgence = findViewById(R.id.searchBoxbyAgence);
        EditText searchBoxDestination = findViewById(R.id.searchBoxbyDestination);

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
                    db.collection("Voyages")
                            .orderBy("nom_agence", Query.Direction.ASCENDING)
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
                            Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    //do the search peix whereequal less than
                    db.collection("Voyages")

                            //.whereGreaterThan(("prix").toLowerCase(),s.toString())
                            //.whereEqualTo(("nom_agence").toLowerCase(),s.toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    list.clear();
                                    for (DocumentSnapshot snapshot : task.getResult()){
                                        try{
                                            int prixx = Integer.parseInt(snapshot.getString("prix"));
                                            int prixTapé =Integer.parseInt(s.toString());
                                            if( prixx <= prixTapé){
                                                ModelVoyagePrix model = new ModelVoyagePrix(snapshot.getString("id") , snapshot.getString("nom_agence") , snapshot.getString("prix"), snapshot.getString("destination") , snapshot.getString("periode"), snapshot.getString("nombre_places") , snapshot.getString("description"));
                                                list.add(model);
                                                Log.d("PRIX","else yess");
                                            }else{
                                                Log.d("PRIX","else no");
                                            }

                                        } catch(NumberFormatException ex){
                                            Log.d("PRIX","CATCH");
                                            db.collection("Voyages")
                                                    .orderBy("nom_agence", Query.Direction.ASCENDING)
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
                                                    Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }

                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }





            }
        });

        //search by Destination
        searchBoxDestination.addTextChangedListener(new TextWatcher() {
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
                    db.collection("Voyages")
                            .orderBy("destination", Query.Direction.ASCENDING)
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
                            Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    //do the search whereequal
                    db.collection("Voyages")
                            .whereEqualTo("destination",s.toString())
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
                            Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }





            }
        });



    }


    public void showData() { //not used yet
        Log.d("SHOWDATA","Show data");
        db.collection("Voyages")
                .orderBy("nom_agence", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            if (snapshot.getString("nom_agence") != null) {
                                ModelVoyagePrix model = new ModelVoyagePrix(snapshot.getString("id"), snapshot.getString("nom_agence"), snapshot.getString("prix"), snapshot.getString("destination"), snapshot.getString("periode"), snapshot.getString("nombre_places"), snapshot.getString("description"));
                                list.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

        /*Original before search
         db.collection("Voyages").orderBy("nom_agence", Query.Direction.ASCENDING).get()
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
                Toast.makeText(MesPatientsVoyagePrix2.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
         */



    //new search
    //@Override
    /*
    public boolean onCreateOptionMenu(Menu menu){
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.menu_main,menu);
        //search view
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when we press search button
                searchData(s); //fun called with string entered in searchview as parameter
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called as and when we type even a single letter
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void searchData(String s){

    }

    public boolean onOptionsItemSelected(MenuItem item){
        //handle other menu item clicks here
        if(item.getItemId()==R.id.action_settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
*/

}