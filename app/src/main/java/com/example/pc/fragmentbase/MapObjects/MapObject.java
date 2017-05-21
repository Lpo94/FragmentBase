package com.example.pc.fragmentbase.MapObjects;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.R;
import com.example.pc.fragmentbase.Other.StaticValues;

/**
 * Created by SharkGaming on 07/05/2017.
 */


public class MapObject extends GameObject
{
    public enum ObjectType { box, flat, slopingRight, slopingLeft, fence}
    private ObjectType type;

    public MapObject(Point _pos, ObjectType _type)
    {

        isSolid = true;
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 5;
        frameCount = 1;
        type = _type;
        bitmap =BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.map_objects);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;

        switch (_type)
        {
            case box:
                currentFrame = 0;
                break;

            case flat:
                currentFrame = 1;
                break;

            case slopingRight:
                currentFrame = 2;
                break;

            case slopingLeft:
                currentFrame = 3;
                break;

            case fence:
                currentFrame = 4;
                break;

        }
    }
}
