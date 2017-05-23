package com.example.pc.fragmentbase.Fragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.Other.SoundEffectManager;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Menu_Main extends Fragment {

    public Fragment_Menu_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        StaticValues.Instance().vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        View view = inflater.inflate(R.layout.fragment_menu_main, container, false);

        view.findViewById(R.id.Start_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).changeFragment("MenuModeSelection");
                StaticValues.Instance().vibrator.vibrate(100);
            }
        });

        view.findViewById(R.id.HTP_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).changeFragment("HowToPlay");
                StaticValues.Instance().vibrator.vibrate(100);
            }
        });

        view.findViewById(R.id.Exit_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.exit(0);
            }
        });


        return  view;
    }
}
