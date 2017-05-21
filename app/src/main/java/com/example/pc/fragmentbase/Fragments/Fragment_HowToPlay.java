package com.example.pc.fragmentbase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.fragmentbase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_HowToPlay extends Fragment {


    public Fragment_HowToPlay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment__how_to_play, container, false);

        view.findViewById(R.id.Back_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Main()).commit();
            }
        });

        view.findViewById(R.id.Previous_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Something here
            }
        });

        view.findViewById(R.id.Next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Something here
            }
        });

        return view;
    }
}
