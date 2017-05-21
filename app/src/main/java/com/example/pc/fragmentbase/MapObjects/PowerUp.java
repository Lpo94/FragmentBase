package com.example.pc.fragmentbase.MapObjects;

import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pc.fragmentbase.Other.GameObject;
import com.example.pc.fragmentbase.Other.Player;
import com.example.pc.fragmentbase.R;
import com.example.pc.fragmentbase.Other.StaticValues;
import com.example.pc.fragmentbase.Other.iCollectable;

import java.util.ArrayList;

/**
 * Created by SharkGaming on 09/05/2017.
 */

public class PowerUp extends GameObject implements iCollectable
{
    public ArrayList<Player> hasCollected;
    public static boolean Clickable = false;
    public enum PowerUpType { fireball, speed, none}
    private PowerUpType type = PowerUpType.none;
    public PowerUpType getType() { return  type;}

    public PowerUp(Point _pos, PowerUpType _type)
    {
        hasCollected = new ArrayList<>();
        isSolid = false;
        setPos(_pos);
        setType(_type);
    }

    private void setType(PowerUpType _type)
    {
        type = _type;

        switch (type)
        {
            case fireball:
                bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.pickup_fireball);
                rowsInSheet = 1;
                columnsInSheet = 8;
                bitmapHeight = bitmap.getHeight() / rowsInSheet;
                bitmapWidth = bitmap.getWidth() / columnsInSheet;
                animationDelay = 50;
                frameCount = 7;
                break;

            case speed:
                bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.pickup_speed);
                rowsInSheet = 1;
                columnsInSheet = 4;
                bitmapHeight = bitmap.getHeight() / rowsInSheet;
                bitmapWidth = bitmap.getWidth() / columnsInSheet;
                animationDelay = 200;
                frameCount = 3;
                break;

            case none:
                break;
        }
    }


    @Override
    public boolean canCollect(Player _player)
    {
        if(hasCollected.contains(_player))
        {
            return false;
        }
        else
        {
            collect(_player);
            return true;
        }
    }

    @Override
    public void collect(Player _player)
    {
        hasCollected.add(_player);

        if(this.getType() == PowerUpType.fireball)
        {
            _player.canShoot = true;
            _player.canSprint = false;
        }

        if(this.getType() == PowerUpType.speed)
        {
            _player.canShoot = false;
            _player.canSprint = true;
        }

        if(hasCollected.size() > 3)
        {
            removeThis();
        }
    }

    public void use(Player _player)
    {
        switch (type)
        {
            case fireball:
                Fireball fireball = new Fireball(_player);
                StaticValues.Instance().gameObjects.add(fireball);
                type = PowerUpType.none;
                break;

            case speed:
                _player.sprintTimer = StaticValues.Instance().currentTime + 5000;
                _player.sprint();
                type = PowerUpType.none;
                break;

            case none:
                break;
        }
    }

    private void removeThis()
    {
        StaticValues.Instance().gameObjects.remove(this);
    }
}
