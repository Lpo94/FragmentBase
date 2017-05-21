package com.example.pc.fragmentbase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pc.fragmentbase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Menu_ModeSelection extends Fragment {


    public Fragment_Menu_ModeSelection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__menu__mode_selection, container, false);


        view.findViewById(R.id.btnSingleP).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Game()).commit();
            }
        });

        view.findViewById(R.id.btnBluetooth).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Bluetooth()).commit();
            }
        });

        view.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Main()).commit();
            }
        });

        return  view;
    }
}

