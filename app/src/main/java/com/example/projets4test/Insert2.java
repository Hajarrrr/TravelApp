package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Insert2 extends AppCompatActivity {

    private EditText nom,phone,cin,specialite;
    private Button mSave;
    private ImageView z;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        nom = findViewById(R.id.prix_insert);
        phone = findViewById(R.id.destination_insert);
        cin = findViewById(R.id.periode_insert);
        specialite = findViewById(R.id.nombre_places_insert);
        z = findViewById(R.id.z);

        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Insert2.this, DataCrud2.class));
                finish();
            }
        });


        db = FirebaseFirestore.getInstance();

        mSave = findViewById(R.id.save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nom = nom.getText().toString();
                String Phone = phone.getText().toString();
                String Cin = cin.getText().toString();
                String Specialite = specialite.getText().toString();


                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String id = current_user.getUid();
                String email = current_user.getEmail();


                //String id = UUID.randomUUID().toString();

                saveToFirestore(id, Nom,Phone,Cin,Specialite, email);


            }
        });

    }
    private  void saveToFirestore(String id,String Nom,String Phone,String Cin, String Specialite, String email){

        if(!Nom.isEmpty() && !Phone.isEmpty() && !Cin.isEmpty() && !Specialite.isEmpty()){

            HashMap<String,Object > map = new HashMap<>();
            map.put("id",id);
            map.put("email",email);
            map.put("nom",Nom);
            map.put("telephone",Phone);
            map.put("cin",Cin);
            map.put("specialite",Specialite);


            db.collection("Voyageur").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Insert2.this,"Data saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Insert2.this,"Failed!!", Toast.LENGTH_SHORT).show();

                }
            });



        }else{
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();;
        }
    }

}