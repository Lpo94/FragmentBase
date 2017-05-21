package com.example.pc.fragmentbase.Other;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pc.fragmentbase.MapObjects.FireObject;
import com.example.pc.fragmentbase.MapObjects.Goal;
import com.example.pc.fragmentbase.MapObjects.Ground;
import com.example.pc.fragmentbase.MapObjects.Mud;
import com.example.pc.fragmentbase.MapObjects.Platform;
import com.example.pc.fragmentbase.MapObjects.PowerUp;
import com.example.pc.fragmentbase.MapObjects.RaceCountdownTimer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by LP on 19-04-2017.
 */

public class LevelCreator {
    private ArrayList<GameObject> currentLevel= new ArrayList<>();
    private ArrayList<GameObject> testLevel = new ArrayList<>();
    private int x = 1;
    private int y = 1;
    private int mapLength;

    private int xPos = 1;
    private int yPos = 1;

    AssetManager mngr;
    InputStream is;
    BufferedReader br;
    String map[][];
    String[] ary;

    private String readFromFile(Context context) {

        String ret = "";

        try {
            mngr = StaticValues.Instance().staticContext.getAssets();
            is = mngr.open("map3.txt");

            if ( is != null ) {


                br = new BufferedReader(new InputStreamReader(is));

//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = br.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                is.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return ret;
    }

    public LevelCreator(int LevelIndex)
    {
//        staticContext = _context;
        if(StaticValues.Instance().gameObjects.size() != 0) {
            StaticValues.Instance().gameObjects.clear();
        }

        if(currentLevel.size() != 0) {
            currentLevel.clear();
        }

        String testString = readFromFile(StaticValues.Instance().staticContext);
        ary = testString.split("");
        mapLength = ary.length;



        switch(LevelIndex)
        {
            case 0:


                for(int x = 1; x <= mapLength-1;x++) {
                    switch (ary[x]) {
                        case "A":
                            Player player = new Player(new Point(StaticValues.Instance().SCREEN_WIDTH / 2, StaticValues.Instance().SCREEN_HEIGHT / 2));

                            if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
                            {
                                StaticValues.Instance().btPlayer = new MultiplayerObject(new Point(StaticValues.Instance().SCREEN_WIDTH / 2, StaticValues.Instance().SCREEN_HEIGHT / 2));
                            }
                            StaticValues.Instance().globalPlayer = player;
                            StaticValues.Instance().allPlayers.add(player);



                            RaceCountdownTimer counter = new RaceCountdownTimer(player, new Point(StaticValues.Instance().SCREEN_WIDTH / 2, StaticValues.Instance().SCREEN_HEIGHT / 5));
                            testLevel.add(counter);
                            
/*
/*                            PowerUp testFireballPUP = new PowerUp(new Point(500, 50), PowerUp.PowerUpType.fireball);
                            testLevel.add(testFireballPUP);*/

//                            PowerUp testSpeedPUP = new PowerUp(new Point(400, 50), PowerUp.PowerUpType.speed);
//                            testLevel.add(testSpeedPUP);
//
//                           Goal testGoal2 = new Goal(new Point(1000, 1000));
//                            testLevel.add(testGoal2);

                 /*           FireObject testfireObject = new FireObject(new Point(800, 50));
                            testLevel.add(testfireObject);*/

                            break;
                        case "B":
                            Mud mud = new Mud(new Point(StaticValues.Instance().gridWidth * xPos, StaticValues.Instance().gridHeight * yPos));
                            testLevel.add(mud);
/*                                if (_i == 3) {
                                    xPos = 10500;
                                    yPos = 800;
                                }
                                if (_i < 7) {
                                    Mud mud = new Mud(new Point(xPos, yPos), 1, 12, R.drawable.mud, 150, 11);
                                    testLevel.add(mud);
                                    xPos += 1200;
                                }*/
                            break;
                        case "C":
                            if(groundCheckAroundMe(x, "C") != -1) {
                                int multiX = groundCheckAroundMe(x, "C");
                                Ground ground = new Ground(new Point(StaticValues.Instance().gridWidth * (xPos-multiX), StaticValues.Instance().gridHeight * yPos), new Rect(0, 0, StaticValues.Instance().gridWidth*multiX, StaticValues.Instance().gridHeight));
                                testLevel.add(ground);
                            }
                            break;

                        case "D":

                                PowerUp testSpeedPUP2 = new PowerUp(new Point(700, 500), PowerUp.PowerUpType.speed);
                                StaticValues.Instance().gameObjects.add(testSpeedPUP2);
                            break;
                        case"E":
                            if(groundCheckAroundMe(x, "E") != -1) {
                            int multiX = groundCheckAroundMe(x, "E");
                            Platform platform = new Platform(new Point(StaticValues.Instance().gridWidth * (xPos-multiX), StaticValues.Instance().gridHeight * yPos), new Rect(0, 0, StaticValues.Instance().gridWidth*multiX, StaticValues.Instance().gridHeight), false);
                            testLevel.add(platform);
                            }
                            break;

                        case "F":
                            if(groundCheckAroundMe(x, "F") != -1) {
                                int multiX = groundCheckAroundMe(x, "F");
                                Platform platform = new Platform(new Point(StaticValues.Instance().gridWidth * (xPos-multiX), StaticValues.Instance().gridHeight * yPos), new Rect(0, 0, StaticValues.Instance().gridWidth*multiX, StaticValues.Instance().gridHeight), true);
                                testLevel.add(platform);
                            }
/*                            PowerUp testFireballPUP2 = new PowerUp(new Point(500, 500), PowerUp.PowerUpType.fireball);
                            StaticValues.Instance().tempObjects.add(testFireballPUP2);*/
                            break;

                        case "G":
                            Goal testGoal = new Goal(new Point(StaticValues.Instance().gridWidth * xPos, StaticValues.Instance().gridHeight * yPos));
                            testLevel.add(testGoal);
                            break;

                        case "M":
                            FireObject fire = new FireObject(new Point(StaticValues.Instance().gridWidth * xPos, StaticValues.Instance().gridHeight * yPos));
                            testLevel.add(fire);
                            break;

                        case "P":
                            PowerUp testFireballPUP = new PowerUp(new Point(StaticValues.Instance().gridWidth * xPos, StaticValues.Instance().gridHeight * yPos), PowerUp.PowerUpType.fireball);
                            testLevel.add(testFireballPUP);
                            break;

                        case "X":
                            break;

                        case "n":
                            yPos++;
                            xPos = 0;
                            break;

                        default:
                            break;
                    }
                    xPos++;
                }

                }
                currentLevel = testLevel;

        for(GameObject go: currentLevel)
        {
            StaticValues.Instance().gameObjects.add(go);
        }
    }

    private int groundCheckAroundMe(int _x, String _object)
    {
        int pos = _x;
        int i = 1;
        if(!checkInfrontofMe(_x, _object))
        {
            while(pos >= 0)
            {
                if(ary[pos-1].equals(_object))
                {
                    i++;
                    pos--;
                }
                else
                {
                    break;
                }

            }
        }
        if(checkInfrontofMe(_x, _object))
        {
            i = -1;
        }

        return i;
    }

    private boolean checkInfrontofMe(int _x, String _object)
    {
        boolean returnValue = false;
        if(_x+1 < mapLength)
        {
            if(ary[_x+1].equals(_object))
            {
                return true;
            }
        }


        return returnValue;
    }
}
