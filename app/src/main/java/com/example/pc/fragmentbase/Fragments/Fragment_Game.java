package com.example.pc.fragmentbase.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.fragmentbase.Other.GameView;
import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Game extends Fragment {



    MainActivity main;
    private GameView gameView;
    public ArrayList<String> finishedPlayers;

    public Fragment_Game() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_game, container, false);

    /*    gameView = new GameView(main.context);*/


        gameView = (GameView) view.findViewById(R.id.ourGameview);

        main = MainActivity.getInstance();
        main.finishedPlayers = new ArrayList<>();
        main.finishedPlayers.add("Player1");
        main.finishedPlayers.add("Player2");
        main.finishedPlayers.add("Player3");
        main.finishedPlayers.add("Player4");


/*
       view.findViewById(R.id.Start_button_menu).setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Endscreen()).commit();
           }
       });
*/


        return  view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        main = (MainActivity)getContext();
    }
}
