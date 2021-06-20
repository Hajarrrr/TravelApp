/*package com.example.firestorecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adapter);
    }
}*/


package com.example.projets4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapterAgences extends RecyclerView.Adapter<MyAdapterAgences.MyViewHolder> {
    private AdminGererAgences activity;
    private List<ModelVoyageurs> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapterAgences(AdminGererAgences activity , List<ModelVoyageurs> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        ModelVoyageurs item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());
        //id,nom,cin,email,specialite,telephone;
        bundle.putString("nom" , item.getNom());
        bundle.putString("cin" , item.getCin());
        bundle.putString("email" , item.getEmail());
        bundle.putString("specialite" , item.getSpecialite());
        bundle.putString("telephone" , item.getTelephone());

        Intent intent = new Intent(activity , AdminGererAgences.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        ModelVoyageurs item = mList.get(position);
        db.collection("Doctor").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_agences, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //id,nom,cin,email,specialite,telephone;
        holder.nom.setText(mList.get(position).getNom());
        holder.cin.setText(mList.get(position).getCin());
        holder.email.setText(mList.get(position).getEmail());
        holder.specialite.setText(mList.get(position).getSpecialite());
        holder.telephone.setText(mList.get(position).getTelephone());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //id,nom,cin,email,specialite,telephone;
        TextView nom,cin,email,specialite,telephone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nom);
            cin = itemView.findViewById(R.id.cin);
            email = itemView.findViewById(R.id.email);
            specialite = itemView.findViewById(R.id.specialite);
            telephone = itemView.findViewById(R.id.telephone);
        }
    }

}

