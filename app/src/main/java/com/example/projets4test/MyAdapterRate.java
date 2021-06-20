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

public class MyAdapterRate extends RecyclerView.Adapter<MyAdapterRate.MyViewHolder> {
    private MesRate activity;
    private List<ModelRate> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapterRate(MesRate activity , List<ModelRate> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        ModelRate item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("Id" , item.getId());
        bundle.putString("nom" , item.getNom());
        bundle.putString("rate" , item.getRate());


        Intent intent = new Intent(activity , MesPatients.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        ModelRate item = mList.get(position);
        db.collection("Rate").document(item.getId()).delete()
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
        View v = LayoutInflater.from(activity).inflate(R.layout.item_rate, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom.setText(mList.get(position).getNom());
        holder.rate.setText(mList.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nom ,rate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nom);
            rate = itemView.findViewById(R.id.rate);
        }
    }

}

