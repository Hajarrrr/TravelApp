package com.example.projets4test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class AjouterVoyage extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_voyage);
    }*/

    private EditText nom_agence,prix,destination,periode,nombre_places,description;
    private Button mSave;
    private ImageView z;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_voyage);


        //nom_agence = findViewById(R.id.nom_agence);
        prix = findViewById(R.id.prix_insert);
        destination = findViewById(R.id.destination_insert);
        periode = findViewById(R.id.periode_insert);
        nombre_places = findViewById(R.id.nombre_places_insert);
        description = findViewById(R.id.description_insert);
        z = findViewById(R.id.z);

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.AjouterVoyage.this, Space1.class));
                finish();
            }
        });


        db = FirebaseFirestore.getInstance();

        mSave = findViewById(R.id.save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String Nom_agence = nom_agence.getText().toString();

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

                String Nom_agence = current_user.getEmail().substring(0, current_user.getEmail().lastIndexOf("@"));
                String Prix = prix.getText().toString();
                String Destination=destination.getText().toString();
                String Periode=periode.getText().toString();
                String Nombre_places=nombre_places.getText().toString();
                String Description = description.getText().toString();

                String id = current_user.getUid();
                //String email = current_user.getEmail().replaceAll("@(.*).(.*)", "");;
                //String email = current_user.getEmail().substring(0, current_user.getEmail().lastIndexOf("@"));

                /*
                db.collection("Doctor").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                for (DocumentSnapshot snapshot : task.getResult()){
                                    if((snapshot.getString("id")).equalsIgnoreCase(id)){

                                        String Nom_agence =  snapshot.getString("nom");
                                        Toast.makeText(com.example.projets4test.AjouterVoyage.this,"yes "+Nom_agence, Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(com.example.projets4test.AjouterVoyage.this,"no ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AjouterVoyage.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                */


                /*
                DocumentReference documentReference = db.collection("Doctor").document(id);

                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if((snapshot.getString("id")).equalsIgnoreCase(id)){

                            String Nom_agence =  snapshot.getString("nom");
                            Toast.makeText(com.example.projets4test.AjouterVoyage.this,"yes "+Nom_agence, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                */
/*
                db.collection("Doctor").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null){
                        String  Nom_agence = task.getResult().getString("nom");
                        //other stuff
                    }else{
                        Toast.makeText(com.example.projets4test.AjouterVoyage.this,"no ", Toast.LENGTH_SHORT).show();
                    }
                });
 */







/*
                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String id = current_user.getUid();
                String email = current_user.getEmail();
*/

                //String id = UUID.randomUUID().toString();
                //String id = current_user.getUid();

                saveToFirestore(id, Nom_agence,Prix,Destination,Periode, Nombre_places,Description);


            }
        });

    }
    private  void saveToFirestore(String id,String nom_agence,String prix,String destination, String periode, String nombre_places, String description){

        if(!prix.isEmpty() && !destination.isEmpty() && !periode.isEmpty() && !nombre_places.isEmpty() && !description.isEmpty()){

            HashMap<String,Object > map = new HashMap<>();
            map.put("id",id);
            map.put("nom_agence",nom_agence);
            map.put("prix",prix);
            map.put("destination",destination);
            map.put("periode",periode);
            map.put("nombre_places",nombre_places);
            map.put("description",description);


            db.collection("Voyages").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(com.example.projets4test.AjouterVoyage.this,"Data saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(com.example.projets4test.AjouterVoyage.this,"Failed!!", Toast.LENGTH_SHORT).show();

                }
            });



        }else{
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();;
        }
    }

}

