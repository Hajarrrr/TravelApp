package com.example.projets4test.ui.chef;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projets4test.ChefSpace;
import com.example.projets4test.Login1;
import com.example.projets4test.R;
import com.example.projets4test.Register2;

public class chef extends Fragment {

    Button button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chef, container, false);

        button = root.findViewById(R.id.chef);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), Login1.class);
                startActivity(i);
            }
        });

        return root;



    }
}
