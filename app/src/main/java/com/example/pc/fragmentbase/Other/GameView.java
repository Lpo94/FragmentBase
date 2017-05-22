package com.example.pc.fragmentbase.Other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.nio.charset.Charset;
import java.util.ArrayList;

import com.example.pc.fragmentbase.MapObjects.PowerUpButton;
/**
 * Created by Lars-Peter on 16/05/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static GameThread gameThreadThread;
    private GestureDetector gestureDetector;
    private long frameTime;
    private LevelCreator levelCreator;
    private PowerUpButton powerBtn;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        gameThreadThread = new GameThread(getHolder(), this);
        setFocusable(true);
        StaticValues.Instance().staticContext = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
        gestureDetector.setIsLongpressEnabled(true);
        SoundEffectManager.getInstance().loadSounds(context);
        newGame();
    }

    public void newGame() {
        StaticValues.Instance().allPlayers = new ArrayList<>();
        StaticValues.Instance().finishedPlayers = new ArrayList<>();
        StaticValues.Instance().colliders = new ArrayList<>();
        StaticValues.Instance().gameObjects = new ArrayList<>();
        StaticValues.Instance().objectsToRemove = new ArrayList<>();
        StaticValues.Instance().tempObjects = new ArrayList<>();
        levelCreator = new LevelCreator(0);
        powerBtn = PowerUpButton.getInstance();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThreadThread = new GameThread(getHolder(), this);
        gameThreadThread.setRunning(true);
        gameThreadThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry) {
            try {
                gameThreadThread.setRunning(false);
                gameThreadThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
       int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++)
        {

            int x = (int) event.getX(i);
            int y = (int) event.getY(i);

            if (!StaticValues.Instance().gameFinished)
            {
                switch (event.getAction() & MotionEvent.ACTION_MASK)
                {

                    case MotionEvent.ACTION_DOWN:

                        if (StaticValues.Instance().globalPlayer != null)
                        {
                            if (x < PowerUpButton.getInstance().pos.x + PowerUpButton.getInstance().bitmapWidth &&
                                    x > PowerUpButton.getInstance().pos.x &&
                                    y < PowerUpButton.getInstance().pos.y + PowerUpButton.getInstance().bitmapHeight &&
                                    y > PowerUpButton.getInstance().pos.y)
                            {
                                StaticValues.Instance().globalPlayer.usePowerup();
                            }
                            else if (x < StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                StaticValues.Instance().globalPlayer.setDirection(-1);
                            }
                            else if (x > StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                StaticValues.Instance().globalPlayer.setDirection(1);
                            }

                        }
                        break;


                    case MotionEvent.ACTION_UP:
                        StaticValues.Instance().globalPlayer.setDirection(0);
                        break;


                    case MotionEvent.ACTION_POINTER_DOWN:

                        if (StaticValues.Instance().globalPlayer != null)
                        {
                            if (x < PowerUpButton.getInstance().pos.x + PowerUpButton.getInstance().bitmapWidth &&
                                    x > PowerUpButton.getInstance().pos.x &&
                                    y < PowerUpButton.getInstance().pos.y + PowerUpButton.getInstance().bitmapHeight &&
                                    y > PowerUpButton.getInstance().pos.y) {
                                StaticValues.Instance().globalPlayer.usePowerup();
                            }

                            else if (x < StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                StaticValues.Instance().globalPlayer.setDirection(-1);
                            }
                            else if (x > StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                StaticValues.Instance().globalPlayer.setDirection(1);
                            }
                        }
                        break;


                    case MotionEvent.ACTION_POINTER_UP:
                        StaticValues.Instance().globalPlayer.setDirection(0);
                        break;
                }
            }
        }
        return gestureDetector.onTouchEvent(event);
    }

    public void update() {

        if(StaticValues.Instance().gameFinished)
        {
            gameThreadThread.setRunning(false);
        }


        StaticValues.Instance().tempObjects = new ArrayList<>(StaticValues.Instance().gameObjects);
        StaticValues.Instance().currentTime = System.currentTimeMillis();

        StaticValues.Instance().deltaTime = (int)(System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();


        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.update();
        }


        if (StaticValues.Instance().globalPlayer != null) {
            StaticValues.Instance().globalPlayer.update();
            PowerUpButton.getInstance().update();
            for (GameObject go : StaticValues.Instance().tempObjects) {
                StaticValues.Instance().globalPlayer.onCollisionStay(go);
                StaticValues.Instance().globalPlayer.onCollisionEnter(go);

            }
            StaticValues.Instance().globalPlayer.onCollisionExit();

        }


        for (GameObject go : StaticValues.Instance().objectsToRemove) {
            if (StaticValues.Instance().gameObjects.contains(go)) {
                StaticValues.Instance().gameObjects.remove(go);
            }
        }
        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer) {
            StaticValues.Instance().btPlayer.update();
        }

        StaticValues.Instance().objectsToRemove.clear();
    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        _canvas.drawColor(Color.WHITE);


        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.draw(_canvas);
        }
        if (StaticValues.Instance().globalPlayer != null) {
            StaticValues.Instance().globalPlayer.draw(_canvas);
        }
        PowerUpButton.getInstance().draw(_canvas);
        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
        {
            StaticValues.Instance().btPlayer.draw(_canvas);
        }

    }



    public static void moveObjectX(int x) {
        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.pos.x = go.pos.x + x;
        }
    }

    public static void moveObjectY(int y) {
        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.pos.y = go.pos.y + y;
        }
    }

    public void sendPlayers()
    {

        String data = StaticValues.Instance().btPlayer.getPos().x + "|" + StaticValues.Instance().btPlayer.getPos().y;
        StaticValues.Instance().mBTService.write(data.getBytes(Charset.defaultCharset()));
    }

    public void endGame()
    {


        gameThreadThread = null;
   /*     StaticValues.Instance().mBTService.stopService();
        StaticValues.Instance().mBTService = null;
        StaticValues.Instance().resetInstance();*/

/*        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);*/


    }

    public void pause() {
        try {
            gameThreadThread.join();
        } catch (InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }
    }




}