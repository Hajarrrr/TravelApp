package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {


    private EditText nom,phone,cin,specialite;
    private Button update;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nom = findViewById(R.id.unom);
        phone = findViewById(R.id.uphone);
        cin = findViewById(R.id.ucin);
        specialite = findViewById(R.id.uspe);




        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore .getInstance();

        userid = fauth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Doctor").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nom.setText(value.getString("nom"));
                cin.setText(value.getString("cin"));
                phone.setText(value.getString("telephone"));
                specialite.setText(value.getString("specialite"));


            }
        });



        update = findViewById(R.id.upda);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nom = nom.getText().toString();
                String Phone = phone.getText().toString();
                String Cin = cin.getText().toString();
                String Specialite = specialite.getText().toString();


                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String id = current_user.getUid();
                String email = current_user.getEmail();



                updateProfile(Nom,Phone,Cin,Specialite);

                startActivity(new Intent(com.example.projets4test.Update.this, Profile.class));
                finish();


            }
        });
    }

    private void updateProfile(String Nom,String Phone,String Cin, String Specialite) {

        fauth = FirebaseAuth.getInstance();
        userid = fauth.getCurrentUser().getUid();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Doctor").document(userid);

        Map<String ,Object> map = new HashMap<>();
        map.put("nom",Nom);
        map.put("cin",Cin);
        map.put("specialite",Specialite);
        map.put("telephone",Phone);

        documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(com.example.projets4test.Update.this, "Profile updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(com.example.projets4test.Update.this,Profile.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(com.example.projets4test.Update.this, "Profile not updated", Toast.LENGTH_SHORT).show();

            }
        });



    }

}