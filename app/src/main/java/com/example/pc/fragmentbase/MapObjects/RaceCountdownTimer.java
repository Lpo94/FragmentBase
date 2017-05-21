package com.example.pc.fragmentbase.MapObjects;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.Other.Player;
import com.example.pc.fragmentbase.R;
import com.example.pc.fragmentbase.Other.StaticValues;

/**
 * Created by SharkGaming on 21/04/2017.
 */

public class RaceCountdownTimer extends GameObject
{
    // Player _player skal senere laves til at tage en liste med players så vi i multiplayer kan sørge for at dette sker for dem alle
    private Player player;

    public RaceCountdownTimer(Player _player, Point _pos)
    {
        player = _player;
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 6;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.countdown);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 1000;
        frameCount = 6;
    }

    @Override
    public void update()
    {
        pos = new Point(StaticValues.Instance().SCREEN_WIDTH / 2 -(bitmapWidth/2), StaticValues.Instance().SCREEN_HEIGHT / 5);

        if(frameCount > 1)
        {
            elapsedTime = (System.nanoTime() - startTime) / 1000000;

            if(elapsedTime > animationDelay)
            {
                currentFrame++;
                startTime = System.nanoTime();

                if(currentFrame >= frameCount)
                {
                    player.setCanmove(true);
                    StaticValues.Instance().gameObjects.remove(this);
                }
            }
        }
    }
}
