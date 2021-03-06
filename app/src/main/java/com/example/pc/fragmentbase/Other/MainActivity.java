package com.example.pc.fragmentbase.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.example.pc.fragmentbase.Fragments.Fragment_Endscreen;
import com.example.pc.fragmentbase.Fragments.Fragment_Game;
import com.example.pc.fragmentbase.Fragments.Fragment_HowToPlay;
import com.example.pc.fragmentbase.Fragments.Fragment_Menu_Bluetooth;
import com.example.pc.fragmentbase.Fragments.Fragment_Menu_Bluetooth_Server;
import com.example.pc.fragmentbase.Fragments.Fragment_Menu_Main;
import com.example.pc.fragmentbase.Fragments.Fragment_Menu_ModeSelection;
import com.example.pc.fragmentbase.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

/*    husk og lave fragment klasser og ik java klasser. og afvinke de 2 nederste flueben.*/
    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        StaticValues.Instance().SCREEN_WIDTH = dm.widthPixels;
        StaticValues.Instance().SCREEN_HEIGHT = dm.heightPixels;
        StaticValues.Instance().staticContext = this;


        backgroundMusic = MediaPlayer.create(StaticValues.Instance().staticContext, R.raw.menu);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.05f, 0.05f);
        backgroundMusic.start();

        if(findViewById(R.id.fragment_container) != null)
        {
            if(savedInstanceState != null)
            {
                return;
            }
        }
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks()
        {


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

    public void changeFragment(String _newFragment)
    {
        switch (_newFragment)
        {
            case "MainMenu":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Main()).commit();
                break;

            case "HowToPlay":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_HowToPlay()).commit();
                break;

            case "MenuBluetooth":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Bluetooth()).commit();
                break;

            case "MenuBluetoothServer":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_Bluetooth_Server()).commit();
                break;

            case "MenuModeSelection":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Menu_ModeSelection()).commit();
                break;

            case "Game":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Game()).commit();
                break;

            case "EndScreen":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Endscreen()).commit();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusic.start();
    }
}
