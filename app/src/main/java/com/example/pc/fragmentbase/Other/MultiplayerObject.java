package com.example.pc.fragmentbase.Other;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.R;

/**
 * Created by Lars-Peter on 16/05/2017.
 */

public class MultiplayerObject extends GameObject    {
    public MultiplayerObject(Point _pos)
    {
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.player_giraf);

        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 14;
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 100;
        frameCount = 14;



    }





}
