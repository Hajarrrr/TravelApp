package com.example.projets4test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {

    TextView nom,cin,phone,spe;
    Button delete,update;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userid;
    private ImageView y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        y = findViewById(R.id.y);
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Profile.this, com.example.projets4test.DataCrud.class));
                finish();
            }
        });



        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.projets4test.Profile.this, com.example.projets4test.Update.class));
                finish();
            }
        });



        nom = findViewById(R.id.getname);
        cin = findViewById(R.id.getcin);
        phone = findViewById(R.id.getphone);
        spe = findViewById(R.id.getspe);

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
                spe.setText(value.getString("specialite"));


            }
        });
    }


    public  void DeleteProfile(View view){
        showDialog();
    }

    private void showDialog(){


        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore .getInstance();

        userid = fauth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Doctor").document(userid);

        AlertDialog.Builder builder = new AlertDialog.Builder(com.example.projets4test.Profile.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete profile ? ");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                documentReference.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(com.example.projets4test.Profile.this, "Profile deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}