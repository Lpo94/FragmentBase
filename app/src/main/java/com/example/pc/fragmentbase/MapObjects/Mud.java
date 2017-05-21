package com.example.pc.fragmentbase.MapObjects;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.R;
import com.example.pc.fragmentbase.Other.StaticValues;

/**
 * Created by Shark on 25-04-2017.
 */

public class Mud extends GameObject
{

    public Mud(Point _pos)
    {
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 12;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.mud);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 150;
        frameCount = 11;
    }

}
