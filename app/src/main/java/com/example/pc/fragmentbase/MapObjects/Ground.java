package com.example.pc.fragmentbase.MapObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pc.fragmentbase.Other.GameObject;

/**
 * Created by LP on 25-04-2017.
 */

public class Ground extends GameObject {

    public Ground(Point _pos, Rect _rect)
    {
        isSolid = true;
        rect = _rect;
        //rect = new Rect(1000,100,20000,200);
        pos = _pos;
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        _canvas.drawRect(rect,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(48);
        _canvas.drawText("X:" + pos.x + " Y:" + pos.y,pos.x,pos.y,paint);
    }
}
