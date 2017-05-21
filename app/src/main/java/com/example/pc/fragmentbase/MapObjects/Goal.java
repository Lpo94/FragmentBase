package com.example.pc.fragmentbase.MapObjects;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.Other.GameView;
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

    public ArrayList<Player> hasCollected;
    GameView gv;

    public Goal(Point _pos)
    {
        hasCollected = new ArrayList<>();
  /*      gv = GameView.getInstance(StaticValues.staticContext); Sæt den her til null så vi ik riskierer at få flere overlappende spil */ // KASPER HER!!

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
        return true;
    }

    @Override
    public void collect(Player _player)
    {
        hasCollected.add(_player);

        Log.w("finishedplayers size: ", String.valueOf(hasCollected.size()));
        Log.w("allPlayers size: ", String.valueOf(StaticValues.Instance().allPlayers.size()));

        if(hasCollected.size() == StaticValues.Instance().allPlayers.size())
        {
            // game over flyt til score skærm når reset virker
            // Flyt til menu
      /*      gv = null; // Er det her nok?*/ // KASPER HER!!
/*            Intent backToMenu = new Intent(StaticValues.Instance().staticContext, MainActivity.class);
            StaticValues.Instance().staticContext.startActivity(backToMenu);*/
// evt brug shared preferences her istedet for bundle til at vidergive datane

/*            Intent endScreen = new Intent(StaticValues.Instance().staticContext, EndScreenActivity.class);*/
 /*           Bundle customParameter = new Bundle();
            customParameter.putStringArray("finishedPlayers", new String[]
                    {
                            String.valueOf(hasCollected.get(0).getPlayerNumber()),
                            String.valueOf(hasCollected.get(1).getPlayerNumber()),
                            String.valueOf(hasCollected.get(2).getPlayerNumber()),
                            String.valueOf(hasCollected.get(3).getPlayerNumber()),
                    });
            endScreen.putExtras(customParameter);
            StaticValues.Instance().staticContext.startActivity(endScreen);*/
    /*        finish(); */
        }
    }
}
