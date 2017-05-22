package com.example.pc.fragmentbase.MapObjects;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.example.pc.fragmentbase.Fragments.Fragment_Game;
import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.Other.GameView;
import com.example.pc.fragmentbase.Other.MainActivity;
import com.example.pc.fragmentbase.Other.Player;
import com.example.pc.fragmentbase.R;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.Other.iCollectable;

import java.util.ArrayList;

/**
 * Created by Shark on 01-05-2017.
 */

public class Goal extends GameObject implements iCollectable
{

    public Goal(Point _pos)
    {
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 1;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.goal);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        frameCount = 1;
    }


    @Override
    public boolean canCollect(Player _player)
    {
        if(StaticValues.Instance().finishedPlayers.contains(_player))
        {
            return false;
        }
        else
        {
            collect(_player);
            return true;
        }
    }

    @Override
    public void collect(Player _player)
    {
        StaticValues.Instance().finishedPlayers.add(_player);

        if(StaticValues.Instance().finishedPlayers.size() == StaticValues.Instance().allPlayers.size())
        {
            StaticValues.Instance().gameFinished = true;
            // skift til end screen
            Fragment_Game.getInstance().GameOver(); /*test om dette virker og evt kig på den funky singleton Fragnebt:gane er dens construktor skal være private. skal jeg evt lave en private kopi af den her i for at køre game over?
      */  }
    }
}
