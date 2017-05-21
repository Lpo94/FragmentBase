package com.example.pc.fragmentbase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Endscreen extends Fragment {

    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;

    MainActivity main;

    public Fragment_Endscreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_endscreen, container, false);

        main = MainActivity.getInstance();

        firstText = (TextView)view.findViewById(R.id.place1);
        firstText.setText(main.finishedPlayers.get(0));

        secondText = (TextView)view.findViewById(R.id.place2);
        secondText.setText(main.finishedPlayers.get(1));

        thirdText = (TextView)view.findViewById(R.id.place3);
        thirdText.setText(main.finishedPlayers.get(2));

        fourthText = (TextView)view.findViewById(R.id.place4);
        fourthText.setText(main.finishedPlayers.get(3));



        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Main()).commit();
            }
        });

        return  view;
    }
}
