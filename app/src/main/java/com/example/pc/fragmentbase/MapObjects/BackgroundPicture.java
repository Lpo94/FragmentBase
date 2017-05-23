package com.example.pc.fragmentbase.MapObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;

/**
 * Created by Lars-Peter on 23/05/2017.
 */

public class BackgroundPicture extends GameObject{


    public BackgroundPicture(Bitmap _bitmap)
    {
        pos = new Point(0,0);
        rowsInSheet = 1;
        columnsInSheet = 1  ;
        bitmap = _bitmap;
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 100;
        frameCount = 1;
        bitmap = Bitmap.createScaledBitmap(bitmap, StaticValues.Instance().SCREEN_WIDTH, StaticValues.Instance().SCREEN_HEIGHT, false);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
//        rect = new Rect(100,100,200,200);
    }


    @Override
    public void update() {
        super.update();
        pos = new Point(0,0);
    }
}
