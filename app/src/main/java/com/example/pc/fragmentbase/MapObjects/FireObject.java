package com.example.pc.fragmentbase.MapObjects;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.R;
import com.example.pc.fragmentbase.Other.StaticValues;

/**
 * Created by SharkGaming on 20/04/2017.
 */

public class FireObject extends GameObject
{


    public FireObject(Point _pos)
    {
        isSolid = false;
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 3;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.fire);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 100;
        frameCount = 2;
//        rect = new Rect(100,100,200,200);
    }

    public void removeThis()
    {
        StaticValues.Instance().gameObjects.remove(this);
    }
}
