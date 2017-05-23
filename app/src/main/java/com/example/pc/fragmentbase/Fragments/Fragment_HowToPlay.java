package com.example.pc.fragmentbase.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_HowToPlay extends Fragment {

    private int counter = 0;
    private Button next, previous;
    private TextView txt1, txt2, txt3, headLine;

    public Fragment_HowToPlay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_fragment__how_to_play, container, false);

        next = (Button) view.findViewById(R.id.Next_button);
        previous = (Button)view.findViewById(R.id.Previous_button);
        txt1 = (TextView) view.findViewById(R.id.textView);
        txt2 = (TextView) view.findViewById(R.id.textView2);
        txt3 = (TextView) view.findViewById(R.id.textView4);
        headLine = (TextView) view.findViewById(R.id.textView3);
        txt2.setVisibility(view.INVISIBLE);
        txt3.setVisibility(view.INVISIBLE);
        previous.setVisibility(view.INVISIBLE);

        view.findViewById(R.id.Back_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment("MainMenu");
            }
        });

        view.findViewById(R.id.Previous_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 1)
                {
                    previous.setVisibility(v.INVISIBLE);
                    txt2.setVisibility(v.INVISIBLE);
                    txt1.setVisibility(v.VISIBLE);
                    headLine.setText("Gameplay");
                    previous.setVisibility(v.INVISIBLE);
                    counter--;
                }

                else if(counter == 2)
                {
                    txt3.setVisibility(v.INVISIBLE);
                    txt2.setVisibility(v.VISIBLE);
                    headLine.setText("Controls");
                    next.setVisibility(v.VISIBLE);
                    counter--;
                }
            }
        });

        view.findViewById(R.id.Next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter == 0)
                {
                    previous.setVisibility(v.VISIBLE);
                    txt2.setVisibility(v.VISIBLE);
                    txt1.setVisibility(v.INVISIBLE);
                    headLine.setText("Controls");
                    counter++;
                }
                else if(counter == 1)
                {
                    txt3.setVisibility(v.VISIBLE);
                    txt2.setVisibility(v.INVISIBLE);
                    headLine.setText("Singleplayer/Multiplayer");
                    next.setVisibility(v.INVISIBLE);
                    counter++;
                }
            }
        });

        return view;
    }
}
