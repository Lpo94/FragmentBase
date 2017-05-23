package com.example.pc.fragmentbase.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.Other.Player;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Endscreen extends Fragment {

    private SharedPreferences savedRecordPref = PreferenceManager.getDefaultSharedPreferences(StaticValues.Instance().staticContext);
    SharedPreferences.Editor editor = savedRecordPref.edit();


    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;
    private TextView highscore;
    private float allTimeRecord;

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
        highscore = (TextView)view.findViewById(R.id.highscore);

        allTimeRecord = savedRecordPref.getLong("AllTimeRecord", 200);

        if(StaticValues.Instance().finishedPlayers.get(0).playerFinalTime < allTimeRecord)
        {
            allTimeRecord = StaticValues.Instance().finishedPlayers.get(0).playerFinalTime;
            editor.putFloat("AllTimeRecord", allTimeRecord);
            editor.apply();
        }

        highscore.setText("All time fastests: " + allTimeRecord);


        switch (StaticValues.Instance().finishedPlayers.size())
        {
            case 1:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(0).playerFinalTime);
                break;

            case 2:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName + " " +  StaticValues.Instance().finishedPlayers.get(0).playerFinalTime);
                secondText.setText(StaticValues.Instance().finishedPlayers.get(1).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(1).playerFinalTime);
                break;

            case 3:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(0).playerFinalTime);
                secondText.setText(StaticValues.Instance().finishedPlayers.get(1).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(1).playerFinalTime);
                thirdText.setText(StaticValues.Instance().finishedPlayers.get(2).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(2).playerFinalTime);
                break;

            case 4:
                firstText.setText(StaticValues.Instance().finishedPlayers.get(0).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(0).playerFinalTime);
                secondText.setText(StaticValues.Instance().finishedPlayers.get(1).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(1).playerFinalTime);
                thirdText.setText(StaticValues.Instance().finishedPlayers.get(2).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(2).playerFinalTime);
                fourthText.setText(StaticValues.Instance().finishedPlayers.get(3).playerName+ " " +  StaticValues.Instance().finishedPlayers.get(3).playerFinalTime);
                break;
        }


        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Main()).commit();*/
                ((MainActivity)getActivity()).changeFragment("MainMenu");
            }
        });

        return  view;
    }


}
