package com.example.pc.fragmentbase.MapObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pc.fragmentbase.Other.GameObject;

/**
 * Created by Shark on 12-05-2017.
 */

public class Platform extends GameObject {

    private boolean direction = false;
    private int timer = -50;
    public Platform(Point _pos, Rect _rect, Boolean _direction)
    {
        isSolid = true;
        rect = _rect;
        pos = _pos;
        direction = _direction;
    }

    @Override
    public void update() {
        super.update();
        timer ++;
        if(!direction && timer < 0)
        {
            pos.x -= 10;
        }

        else if(!direction && timer > 0)
        {
            pos.x += 10;
        }

        else if(direction && timer < 0)
        {
            pos.y -= 10;
        }

        else if(direction && timer > 0)
        {
            pos.y += 10;
        }

        if(timer >= 50)
        {
            timer = -51;
        }


    }



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
