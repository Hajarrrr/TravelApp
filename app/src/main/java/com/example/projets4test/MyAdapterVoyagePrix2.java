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

public class MyAdapterVoyagePrix2 extends RecyclerView.Adapter<MyAdapterVoyagePrix2.MyViewHolder> {
    private MesPatientsVoyagePrix2 activity;
    private List<ModelVoyagePrix> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapterVoyagePrix2(MesPatientsVoyagePrix2 activity , List<ModelVoyagePrix> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        ModelVoyagePrix item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());

        bundle.putString("nom_agence" , item.getNom_agence());
        //added
        //bundle.putString("search" , item.getNom_agence().toLowerCase());


        bundle.putString("prix" , String.valueOf(item.getPrix()));
        bundle.putString("destination" , item.getDestination());
        bundle.putString("periode" , item.getPeriode());
        bundle.putString("nombre_places" , String.valueOf(item.getNombre_places()));
        bundle.putString("description" , item.getDescription());

        Intent intent = new Intent(activity , MesPatients.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        ModelVoyagePrix item = mList.get(position);
        db.collection("Voyages").document(item.getId()).delete()
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_voyageprix, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom_agence.setText(mList.get(position).getNom_agence());
        holder.prix.setText(mList.get(position).getPrix());
        holder.destination.setText(mList.get(position).getDestination());
        holder.periode.setText(mList.get(position).getPeriode());
        holder.nombre_places.setText(mList.get(position).getNombre_places());
        holder.description.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nom_agence ,prix, destination,periode,nombre_places,description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nom_agence = itemView.findViewById(R.id.nom_agence);
            prix = itemView.findViewById(R.id.prix);
            destination = itemView.findViewById(R.id.destination);
            periode = itemView.findViewById(R.id.periode);
            nombre_places = itemView.findViewById(R.id.nombre_places);
            description = itemView.findViewById(R.id.description);
        }
    }

}

