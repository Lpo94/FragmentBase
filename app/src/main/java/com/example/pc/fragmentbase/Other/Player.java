package com.example.pc.fragmentbase.Other;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;

import com.example.pc.fragmentbase.MapObjects.FireObject;
import com.example.pc.fragmentbase.MapObjects.Fireball;
import com.example.pc.fragmentbase.MapObjects.Goal;
import com.example.pc.fragmentbase.MapObjects.Ground;
import com.example.pc.fragmentbase.MapObjects.Mud;
import com.example.pc.fragmentbase.MapObjects.Platform;
import com.example.pc.fragmentbase.MapObjects.PowerUp;
import com.example.pc.fragmentbase.MapObjects.PowerUpButton;
import com.example.pc.fragmentbase.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LP on 19-04-2017.
 */

public class Player extends GameObject {
    //Singleton
    private static Player instance;

    public static Player Instance()
    {
        if(instance == null)
        {
            instance = new Player(new Point(StaticValues.Instance().SCREEN_WIDTH / 2, StaticValues.Instance().SCREEN_HEIGHT / 2));
        }

        return instance;
    }

    // Generel
    public int colour;
    public boolean canMove;
    private boolean falling = true;
    private boolean jumping = false;
    private boolean slowed = false;
    private float timer = 0;
    private float velocity = 4;
    private float defaultVelocity;
    private int direction = 0;
    private Point referencePoint;
    public String playerName;
    public float playerFinalTime;
    public float time = 0;
    // Animation
    private enum Animations { idle, walking, falling, stunned}
    private Animations curAnim;
    private boolean isStunned; // skal sættes hvis stunned sakl være længere tid en stun animationen for at få den til at loope.
    private boolean startingStun; // skal sættes det øjeblik spilleren bliver stunned, den kører selv automatisk videre til stunned efter.
    private long stunDelay;

    // Powerup
    public boolean canShoot, canSprint, isSprinting;
    public PowerUp currentPowerup;
    public long sprintTimer;

    public void setCanmove(boolean _value)
    {
        canMove = _value;
    }
    public float getSpeed()
    {
        return speed;
    }
    public Point getReferencePoint()
    {
        return referencePoint;
    }

    public Player(Point _pos) {
        super();
        pos = _pos;
        rect = new Rect(100,100,200,200);
        speed = 0.5f;
        defaultVelocity = velocity;
        colour = new Color().GREEN;

        canMove = false;
        falling = true;
        setPlayerSprite(getPlayerNumber());
        curAnim = Animations.idle;
        currentPowerup = new PowerUp(new Point(0, 0), PowerUp.PowerUpType.none);
        StaticValues.Instance().vibrator = (Vibrator) StaticValues.Instance().staticContext.getSystemService(Context.VIBRATOR_SERVICE);
    }


    @Override
    public void update()
    {
        time += 0.05f;

        referencePoint = new Point(0,0);
        pos = new Point(StaticValues.Instance().SCREEN_WIDTH / 2 -(bitmapWidth/2), StaticValues.Instance().SCREEN_HEIGHT / 2);

        manageAnimationStates();

        if(isSprinting) sprint();

        if(rect != null)
        {
            rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2, pos.x+rect.width()/2,pos.y+rect.height()/2);
        }


        if(timer >= 0 && slowed == true)
        {
            timer --;
        }

        else if(timer <= 0 && slowed == true)
        {
            speed = 0.5f;
            slowed = false;
        }

        if(pos.y < 0)
        {
            pos.y = 0;
        }

        if(canMove && !isStunned)
        {
            if(direction != 0) {
                switch (direction) {
                    case -1:

                        referencePoint.x += (int)(speed * StaticValues.Instance().deltaTime);
                        sourceY = bitmapHeight;
                        GameView.moveObjectX((int)(speed * StaticValues.Instance().deltaTime));
//                        pos.x -= speed * StaticValues.deltaTime;
                        break;
                    case 1:

                        referencePoint.x -= (int)(speed * StaticValues.Instance().deltaTime);
//                        pos.x += speed * StaticValues.Instance().deltaTime;
                        sourceY = 0;
                        GameView.moveObjectX((int)-(speed * StaticValues.Instance().deltaTime));
                        break;
                }
            }
            int valueCheck = Math.round(rect.bottom + StaticValues.Instance().WORLD_GRAVITY * StaticValues.Instance().deltaTime);

            if(isObjectSolid(new Point(pos.x,valueCheck)))
            {
                falling = false;

                if(velocity < 0) {
                    jumping = false;
                    velocity = defaultVelocity ;
                }

            }
            else
            {
                falling = true;
            }

            if(falling && !jumping)
            {
                GameView.moveObjectY((int)-(0.5f * StaticValues.Instance().deltaTime));
                referencePoint.y -= (int)(0.5f * StaticValues.Instance().deltaTime);
//                pos.y += StaticValues.Instance().WORLD_GRAVITY * StaticValues.Instance().deltaTime;
            }

            if(jumping)
            {
                GameView.moveObjectY((int)+(velocity * StaticValues.Instance().deltaTime));
                referencePoint.y += (int)(velocity * StaticValues.Instance().deltaTime);
//                pos.y -= velocity * StaticValues.Instance().deltaTime;
                velocity -= StaticValues.Instance().WORLD_GRAVITY * StaticValues.Instance().deltaTime;

                if(velocity <= -2)
                {
                    velocity = -2;
                }
            }
        }

    }

    private void manageAnimationStates()
    {
        // Add så animationDelay falder når man har speedboost for at simulere sprint
        if(canMove && direction == 0 && !isStunned)
        {
            curAnim = Animations.idle;
        }
        if(canMove && direction != 0)
        {
            curAnim = Animations.walking;
        }
        if(startingStun || isStunned)
        {
            if(startingStun)
            {
                curAnim = Animations.falling;
            }
            if(isStunned)
            {
                curAnim = Animations.stunned;
            }

            canMove = false;
            stunTimer();
        }


        elapsedTime = (System.nanoTime() - startTime) / 1000000;

        if(elapsedTime > animationDelay)
        {
            currentFrame++;
            startTime = System.nanoTime();

            switch (curAnim)
            {
                case idle:
                    currentFrame = 0;
                    break;

                case walking:
                    if (currentFrame > 6)
                    {
                        currentFrame = 1;
                    }
                    break;

                case falling:
                    if (currentFrame > 10)
                    {
                        startingStun = false;
                        isStunned = true;
                        stunDelay = StaticValues.Instance().currentTime + 3000;
                    }
                    break;

                case stunned:
                    if (currentFrame > 13)
                    {
                        currentFrame = 10;
                    }
                    break;
            }
        }
    }

    private void stunTimer()
    {
        speed = 0;

        if(StaticValues.Instance().currentTime > stunDelay)
        {
            isStunned = false;
            canMove = true;
            speed = 0.5f;
        }
    }

    public void sprint()
    {
        speed = 1.5f;
        animationDelay = 25;

        if(StaticValues.Instance().currentTime > sprintTimer)
        {
            isSprinting = false;
            speed = 0.5f;
            animationDelay = 75;
        }
    }

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);

        if(canMove)
        {
            if(_other instanceof Mud)
            {
                speed = 0.1f;
                slowed = true;
                timer = 15;
            }

            if(_other instanceof Ground)
            {
                switch(direction)
                {
                    case -1:
                        if(_other.getRect().right > (rect.left + 150) && _other.getRect().bottom > rect.bottom)
                        {
                            GameView.moveObjectX(-75);
                            direction = 0;
                        }
                        break;
                    case 0:
                        break;
                    case 1:
                        if(_other.getRect().right > rect.right && _other.getRect().bottom > rect.bottom)
                        {
                            GameView.moveObjectX(75);
                            direction = 0;
                        }
                        break;
                }
            }

            if(_other instanceof Platform)
            {
                if((_other.getRect().top < rect.bottom && rect.left < _other.getRect().left) || (_other.getRect().top < rect.bottom && rect.right < _other.getRect().right))
                {
                    GameView.moveObjectY(1);
                }
            }

            if(_other instanceof Goal)
            {
                ((Goal) _other).canCollect(this);
            }

            if(_other instanceof Fireball && ((Fireball)_other).owner != this)
            {
                startingStun = true;
                StaticValues.Instance().vibrator.vibrate(100);
            }

            if(_other instanceof FireObject)
            {
                startingStun = true;
                StaticValues.Instance().vibrator.vibrate(100);
                ((FireObject)_other).removeThis();
            }

            if(_other instanceof PowerUp)
            {
                if(((PowerUp)_other).canCollect(this))
                {
                    if(((PowerUp)_other).getType() == PowerUp.PowerUpType.fireball)
                    {
                        currentPowerup = new PowerUp(pos, PowerUp.PowerUpType.fireball);
                        PowerUpButton.getInstance().state = PowerUpButton.btnStates.fire;;
                    }

                    if(((PowerUp)_other).getType() == PowerUp.PowerUpType.speed)
                    {
                        currentPowerup = new PowerUp(pos, PowerUp.PowerUpType.speed);
                        PowerUpButton.getInstance().state = PowerUpButton.btnStates.speed;

                    }
                }
            }
        }
    }


    //-1 = left, 1 = right;
    public void setDirection(int _direction)
    {
        direction = _direction;
    }

    public void playerJump()
    {
        if(!jumping) {
            jumping = true;
        }
    }

    private boolean isObjectSolid(Point _p)
    {
        for(GameObject go: StaticValues.Instance().tempObjects)
        {
            if(go.isSolid)
            {
                if (go.getRect() != null)
                {
                    Rect  r = new Rect(pos.x+bitmapWidth/4, pos.y+bitmapHeight/2, pos.x+bitmapWidth/2, pos.y+bitmapHeight);
                    if (Rect.intersects(go.getRect(),r))
                    {
                        return true;
                    }
                    else if(go.getRect().contains(r))
                    {
                        return true;
                    }
                }
            }
        }
        return  false;
    }

    // Skal implementeres færdigt når multiplayer er færdigt og added.
    public int getPlayerNumber()
    {
 /*       for (int i = 0; i < rooom.participants.lenght; i++)
        {
            if(i = waiting rooom.participants[this])
            {
                return i;
            }
        }*/

        return 2;
    }

    private void setPlayerSprite(int _playerNumber)
    {
        switch (_playerNumber)
        {
            case 1:
                bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.player_giraf);
                playerName ="Giraf";
                break;

            case 2:
                bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.player_dino);
                playerName ="Dino";
                break;

            case 3:
                bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.player_parrot);
                playerName ="Parrot";
                break;

            case 4:
                bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.player_cow);
                playerName ="Cow";
                break;
        }

        rowsInSheet = 2;
        columnsInSheet = 14;
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 50;
        frameCount = 14;
    }

    public void usePowerup()
    {
        currentPowerup.use(this);
        PowerUpButton.getInstance().state = PowerUpButton.btnStates.empty;
    }
}
