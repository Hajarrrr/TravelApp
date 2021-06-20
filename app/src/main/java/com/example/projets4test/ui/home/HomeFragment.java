package com.example.projets4test.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projets4test.AccueilActivity;
import com.example.projets4test.Login;
import com.example.projets4test.MainActivity;
import com.example.projets4test.R;

public class HomeFragment extends Fragment {

    private ImageView sante;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sante= root.findViewById(R.id.imageView5);
        sante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
            }
        });
        return root;

    }
}