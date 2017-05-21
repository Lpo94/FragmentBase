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
public class Fragment_Menu_Bluetooth_Server extends Fragment {


    public Fragment_Menu_Bluetooth_Server() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__menu__bluetooth__server, container, false);
    }

}
