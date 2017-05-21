package com.example.pc.fragmentbase.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.example.pc.fragmentbase.Fragments.Fragment_Game;
import com.example.pc.fragmentbase.Fragments.Fragment_Menu_Main;
import com.example.pc.fragmentbase.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

/*    husk og lave fragment klasser og ik java klasser. og afvinke de 2 nederste flueben.*/
    private static MainActivity instance;

    public static MainActivity getInstance()
    {
        if(instance == null)
        {
            instance = new MainActivity();
        }
        return instance;
    }
    public ArrayList<String> finishedPlayers;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = this;

        finishedPlayers = new ArrayList<>();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        StaticValues.Instance().SCREEN_WIDTH = dm.widthPixels;
        StaticValues.Instance().SCREEN_HEIGHT = dm.heightPixels;

        if(findViewById(R.id.fragment_container) != null)
        {
            if(savedInstanceState != null)
            {
                return;
            }
        }
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks(){


            @Override
               public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
               super.onFragmentAttached(fm, f, context);
               }

               @Override
               public void onFragmentDetached(FragmentManager fm, Fragment f) {
               super.onFragmentDetached(fm, f);
               }
       }, false);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Fragment_Menu_Main()).commit();
    }


    public void StartGame(View view)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Game()).addToBackStack(null).commit();
    }


}
