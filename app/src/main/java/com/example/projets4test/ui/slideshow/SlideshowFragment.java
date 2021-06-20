package com.example.projets4test.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projets4test.Login;
import com.example.projets4test.R;
import com.example.projets4test.Register1;
import com.example.projets4test.Login;

public class SlideshowFragment extends Fragment {

    Button button;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);



        button = root.findViewById(R.id.first);
        button.setOnClickListener(new View.OnClickListener() {
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