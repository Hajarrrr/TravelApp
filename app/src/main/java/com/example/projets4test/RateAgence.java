package com.example.projets4test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RateAgence<list> extends AppCompatActivity {

    //added
    //private EditText mTitle , mDesc;
    private EditText mTitle;
    private Button mSaveBtn, mShowBtn;
    private FirebaseFirestore db;
    //private String uTitle, uDesc , uId;
    private String uTitle, uId;
    private float uDesc;
    //added
    private RatingBar ratingBar;
    float rateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters_rate_agence);

        mTitle = findViewById(R.id.Agence);
        //mDesc = findViewById(R.id.Rate);
        mSaveBtn = findViewById(R.id.save_btn);
        mShowBtn = findViewById(R.id.showall_btn);
        //added
        ratingBar = findViewById(R.id.ratingBar);



        db= FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mSaveBtn.setText("Update");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            //uDesc = bundle.getString("uDesc");
            mTitle.setText(uTitle);
            //mDesc.setText(uDesc);
        }else{
            mSaveBtn.setText("Save");
        }

        View imageView = findViewById(R.id.imageViewa);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RateAgence.this, Space2Patient.class));
                finish();
            }
        });
        
        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RateAgence.this , MesRate.class));
            }
        });



        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mTitle.getText().toString();
                //String desc = mDesc.getText().toString();
                //String desc = String.valueOf(ratingBar.getRating());
                float desc = ratingBar.getRating();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id , title, desc);
                }else{
                    String id = UUID.randomUUID().toString();
                    String desc1= String.valueOf(desc);
                    saveToFireStore(id , title , desc1);
                }

            }
        });


    //added rating

    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            rateValue = ratingBar.getRating();
            Log.d("RATE", String.valueOf(rateValue));
            //if(rateValue <=1 && rateValue>0){

            //}
        }
    });

    //fin rating part

    }
    private void updateToFireStore(String id , String title , float desc){

        db.collection("Rate").document(id).update("nom" , title , "rate" , desc)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RateAgence.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RateAgence.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RateAgence.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }






    private void saveToFireStore(String id , String title , String desc){

        if (!title.isEmpty() && !desc.isEmpty() ){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("nom" , title);
            map.put("rate" , desc);

//collect the agence name and store them in an array pour tester apres

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
                                    //Log.d("YES", snapshot.getString("id") + " => " + snapshot.getString("nom"));
                                }
                            }
                            Log.d("YESSSSSSSSSSS", titleList.toString() );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(com.example.projets4test.RateAgence.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

            //Log.d("YESSSSSSSSSSS", titleList.toString() );


//save rate into the Doctor
            // jointure 2 boucles
            /*
            db.collection("Doctor")
                    .whereEqualTo("nom", title)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (DocumentSnapshot snapshot : task.getResult()) {
                                if (snapshot.getString("nom").equals(title) ) {
                                db.collection("Doctor")
                                        .document(id)
                                        .collection("Rate")
                                        //.whereEqualTo("nom", title)
                                        .document(id)
                                        .set(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                                    }

                                });

                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(com.example.projets4test.RateAgence.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

             */
// jointure 2 boucles fieled
/*
            db.collection("Doctor")
                    .whereEqualTo("nom", title)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (DocumentSnapshot snapshot : task.getResult()) {
                                if (snapshot.getString("nom").equals(title) ) {
                                db.collection("Doctor")
                                        //.whereEqualTo("nom", title)
                                        .document(id)

                                        //.collection("Rate")
                                        //.whereEqualTo("nom", title)
                                        //.document(id)
                                        .set(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                                    }

                                });

                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(com.example.projets4test.RateAgence.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
*/

            /*
            //using update
           DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Doctor").document(id);
            documentReference.update("rate",desc).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(RateAgence.this, "updated", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(Update2.this,Profile2.class));
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RateAgence.this, "Profile not updated", Toast.LENGTH_SHORT).show();

                }
            });
            */


/* USING QUERRIES
            com.google.firebase.firestore.Query query = db.collection("Doctor").whereEqualTo("name", title);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                Boolean questionID = document.getBoolean("nom");
                                String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
                                //document.set(map);
                                Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

 */
            /*
            //using queries
            CollectionReference documentReference = FirebaseFirestore.getInstance().collection("Doctor");
            //CollectionReference questionRef = firebaseFirestore.collection("questions");
            com.google.firebase.firestore.Query query = documentReference.whereEqualTo("nom", title);
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()) {

                        for (DocumentReference doc : queryDocumentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                FriendRequest friendRequest = doc.getDocument().toObject(FriendRequest.class).withId(doc.getDocument().getId());
                                requestList.add(friendRequest);
                                requestAdapter.notifyDataSetChanged();
                                refreshLayout.setRefreshing(false);
                            }

                        }

                        if(requestList.isEmpty()) {
                            refreshLayout.setRefreshing(false);
                            getView().findViewById(R.id.default_item).setVisibility(View.VISIBLE);
                        }

                    }else{
                        refreshLayout.setRefreshing(false);
                        getView().findViewById(R.id.default_item).setVisibility(View.VISIBLE);
                    }


                }
            });

             */





            /*
            db.collection("Doctor")
                    .document(id)
                    .collection("Rate")
                    //.whereEqualTo("nom", title)
                    .document(id)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

             */







            //added
            /*
            Log.d("YES", String.valueOf(titleList) );
            if(titleList.contains(title)){
                db.collection("Rate")
                        .document(id)
                        .set(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Log.d("DIDNTEXIST", "nop");
            }

             */









/////ORIGINALLL


            //insert rate into rate doc
            db.collection("Rate")
                    .document(id)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });








/*
            //insert rate into rate doc
            db.collection("Rate")
                    .document(id)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });
*/



       //this afternoon try
             /*     db.collection("Doctor")
                    .whereEqualTo("nom", title)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (DocumentSnapshot snapshot : task.getResult()) {
                                if (snapshot.getString("nom").equals(title) ) {
                                    db.collection("Doctor")
                                            //.whereEqualTo("nom", title)
                                            .document(id)

                                            //.collection("Rate")
                                            //.whereEqualTo("nom", title)
                                            //.document(id)
                                            .set(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                                                            if (entry.getKey().equals(title)) {
                                                                Log.d("TAG", entry.getValue().toString());
                                                            }
                                                        }
                                                        Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                                        }

                                    });

                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(com.example.projets4test.RateAgence.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
*/


 //           db.collection("Rate")
   //                 .document("Qh5hakxwlgUUMOyq3TSZ")
     //               .update("rate",Integer.parseInt(desc) + snap "rate");
                    //.set(map);

                    /*
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });

                     */















/*
            db.collection("Doctor").document(id)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RateAgence.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RateAgence.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

 */
            /*
            db.collection("Doctor")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot snapshot : task.getResult()){
                                if(snapshot.getString("nom_agence").equals(title.toString())){
                                    Log.d("YES","YES");
                                }
                                else{
                                    Log.d("NO","NO");
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RateAgence.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
            */

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }

    //private void ArrayAgences(){
        //private List<String> list;
        //list = new ArrayList<>();
    //ArrayList<String> list;

    //list = new ArrayList<String>();
    //}

}