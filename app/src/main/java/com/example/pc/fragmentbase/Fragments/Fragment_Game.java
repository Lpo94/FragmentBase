package com.example.pc.fragmentbase.Fragments;


import android.app.Activity;
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

    private GameView gameView;

    private static Fragment_Game instance;


    public static Fragment_Game getInstance()
    {
        if(instance == null)
        {
            instance = new Fragment_Game();
        }
        return instance;
    }


    public Fragment_Game()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        gameView = (GameView) view.findViewById(R.id.ourGameview);

        return  view;
    }

    public void GameOver()
    {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Endscreen()).commit();
    }

}
