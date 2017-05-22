package com.example.pc.fragmentbase.Fragments;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.fragmentbase.Other.SoundManager;
import com.example.pc.fragmentbase.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Menu_Main extends Fragment {

    static MediaPlayer music;

    public Fragment_Menu_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_menu_main, container, false);

        SoundManager.getInstance().startBackgroundMusic();

        view.findViewById(R.id.Start_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
/*                getFragmentManager().popBackStack();*/
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_ModeSelection()).commit();
            }
        });

        view.findViewById(R.id.HTP_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_HowToPlay()).commit();
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
