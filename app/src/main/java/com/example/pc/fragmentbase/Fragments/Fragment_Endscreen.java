package com.example.pc.fragmentbase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Endscreen extends Fragment {

    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;


    public Fragment_Endscreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_endscreen, container, false);

        firstText = (TextView)view.findViewById(R.id.place1);
        secondText = (TextView)view.findViewById(R.id.place2);
        thirdText = (TextView)view.findViewById(R.id.place3);
        fourthText = (TextView)view.findViewById(R.id.place4);

        switch (StaticValues.Instance().finishedPlayers.size())
        {
            case 1:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName);
                break;

            case 2:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName);
                secondText.setText(StaticValues.Instance().finishedPlayers.get(1).playerName);
                break;

            case 3:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName);
                secondText.setText(StaticValues.Instance().finishedPlayers.get(1).playerName);
                thirdText.setText(StaticValues.Instance().finishedPlayers.get(2).playerName);
                break;

            case 4:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName);
                secondText.setText(StaticValues.Instance().finishedPlayers.get(1).playerName);
                thirdText.setText(StaticValues.Instance().finishedPlayers.get(2).playerName);
                fourthText.setText(StaticValues.Instance().finishedPlayers.get(3).playerName);
                break;
        }


        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Main()).commit();
            }
        });

        return  view;
    }
}
