package com.example.pc.fragmentbase.MapObjects;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.R;



public class PowerUpButton extends GameObject
{

    public boolean clickable = false;
    private static PowerUpButton instance;
    public enum btnStates {empty, fire, speed,};
    public btnStates state;
    public static PowerUpButton getInstance()
    {
        if(instance == null)
        {
            instance = new PowerUpButton();
        }
        return instance;
    }

    private PowerUpButton()
    {
        state = btnStates.empty;
        rowsInSheet = 1;
        columnsInSheet = 3;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.powerup_button);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        frameCount = 3;
        pos = new Point(StaticValues.Instance().SCREEN_WIDTH / 2 - (bitmapWidth / 2), calcPosition(StaticValues.Instance().globalPlayer.getPos().y));
    }

    private int calcPosition(int _playerY)
    {
        int y = StaticValues.Instance().SCREEN_HEIGHT / 2 + bitmapHeight;

        for(int i = y; i < _playerY + bitmapHeight; i++)
        {
            y++;
        }

        return y;
    }

    @Override
    public void update()
    {

        switch (state)
        {
            case empty:
                clickable = false;
                currentFrame = 0;
                break;

            case speed:
                clickable = true;
                currentFrame = 1;
                break;

            case fire:
                clickable = true;
                currentFrame = 2;
                break;
        }
    }


    @Override
    public void draw(Canvas _canvas)
    {
        if(bitmap != null)
        {
            sourceX = currentFrame * bitmapWidth;

            Rect sourceRect = new Rect(sourceX, sourceY, sourceX + bitmapWidth, sourceY + bitmapHeight);
            Rect r = new Rect(pos.x, pos.y, pos.x + bitmapWidth, pos.y + bitmapHeight);

            _canvas.drawBitmap(bitmap, sourceRect, r, null);
        }
    }

}
