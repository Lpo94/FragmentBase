package com.example.pc.fragmentbase.Other;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by LP on 19-04-2017.
 */

public class StaticValues {

    private static StaticValues instance;

    public static StaticValues Instance(){
        if(instance == null)
        {
            instance = new StaticValues();
        }
        return instance;
    }


    private StaticValues()
    {

    }

    public void resetInstance()
    {
        instance = null;
    }




    //Player

    public MultiplayerObject btPlayer;
    public ArrayList<Player> allPlayers;
    public ArrayList<Player> finishedPlayers;

    //Lists
    public ArrayList<GameObject> colliders;

    public ArrayList<GameObject> gameObjects;

    public ArrayList<GameObject> tempObjects;

    public ArrayList<GameObject> objectsToRemove;

    public Fragment fragment;


    // Ints
    public int SCREEN_WIDTH;

    public int SCREEN_HEIGHT;

    public int deltaTime;

    public int gridWidth = 60;

    public int gridHeight = 60;

    //Floats
    public float WORLD_SPEED;

    public float WORLD_GRAVITY = 0.02f;

    // boolean
    public boolean gameFinished = false;

    // Longs
    public long currentTime;


    // Smaller things such as Contexts and similar
    public Context staticContext;

    public GameState gameState;


    // Bluetooth

    public BTService mBTService;



}
