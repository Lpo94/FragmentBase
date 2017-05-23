package com.example.pc.fragmentbase.Other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

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
    private BroadcastReceiver receiver;
    private IntentFilter filter;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        gameThreadThread = new GameThread(getHolder(), this);
        setFocusable(true);

        filter = new IntentFilter();
        filter.addAction("PlayerPos");
        filter.addAction("Hello");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction())
                {
                    case "PlayerPos":

                        String i = intent.getStringExtra("xPos");
                        String t = intent.getStringExtra("yPos");
                        Toast.makeText(getContext(), "xPos = " + i, Toast.LENGTH_SHORT).show();

                        int x = Integer.parseInt(i);
                        int y = Integer.parseInt(t);

                        StaticValues.Instance().btPlayer.getPos().x -= x;
                        StaticValues.Instance().btPlayer.getPos().y -= y;

                        break;

                }
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,filter);

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

                        if (Player.Instance() != null)
                        {
                            if (x < PowerUpButton.getInstance().pos.x + PowerUpButton.getInstance().bitmapWidth &&
                                    x > PowerUpButton.getInstance().pos.x &&
                                    y < PowerUpButton.getInstance().pos.y + PowerUpButton.getInstance().bitmapHeight &&
                                    y > PowerUpButton.getInstance().pos.y)
                            {
                                Player.Instance().usePowerup();
                            }
                            else if (x < StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                Player.Instance().setDirection(-1);
                            }
                            else if (x > StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                Player.Instance().setDirection(1);
                            }

                        }
                        break;


                    case MotionEvent.ACTION_UP:
                        Player.Instance().setDirection(0);
                        break;


                    case MotionEvent.ACTION_POINTER_DOWN:

                        if (Player.Instance() != null)
                        {
                            if (x < PowerUpButton.getInstance().pos.x + PowerUpButton.getInstance().bitmapWidth &&
                                    x > PowerUpButton.getInstance().pos.x &&
                                    y < PowerUpButton.getInstance().pos.y + PowerUpButton.getInstance().bitmapHeight &&
                                    y > PowerUpButton.getInstance().pos.y) {
                                Player.Instance().usePowerup();
                            }

                            else if (x < StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                Player.Instance().setDirection(-1);
                            }
                            else if (x > StaticValues.Instance().SCREEN_WIDTH / 2)
                            {
                                Player.Instance().setDirection(1);
                            }
                        }
                        break;


                    case MotionEvent.ACTION_POINTER_UP:
                        Player.Instance().setDirection(0);
                        break;
                }
            }
        }
        return gestureDetector.onTouchEvent(event);
    }

    public void update() {




        if(StaticValues.Instance().gameFinished)
        {
            MainActivity main = (MainActivity)getContext();
            main.changeFragment("EndScreen");
        }


        StaticValues.Instance().tempObjects = new ArrayList<>(StaticValues.Instance().gameObjects);
        StaticValues.Instance().currentTime = System.currentTimeMillis();

        StaticValues.Instance().deltaTime = (int)(System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();


        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.update();
        }


        if (Player.Instance() != null) {
            Player.Instance().update();
            PowerUpButton.getInstance().update();
            for (GameObject go : StaticValues.Instance().tempObjects) {
                Player.Instance().onCollisionEnter(go);
            }
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

        if(Player.Instance().getReferencePoint().x != 0 ||
                Player.Instance().getReferencePoint().y != 0 ) {
            String fixes = "PlayerDifference/"+Player.Instance().getReferencePoint().x
                    +"/"+Player.Instance().getReferencePoint().y+"/";
            StaticValues.Instance().mBTService.write(fixes.getBytes(Charset.defaultCharset()));
        }
    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        _canvas.drawColor(Color.WHITE);


        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.draw(_canvas);
        }
        if (Player.Instance() != null) {

            Player.Instance().draw(_canvas);


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
        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
        {
            StaticValues.Instance().btPlayer.getPos().x = StaticValues.Instance().btPlayer.getPos().x + x;
        }
    }

    public static void moveObjectY(int y) {
        for (GameObject go : StaticValues.Instance().tempObjects) {
            go.pos.y = go.pos.y + y;
        }

        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
        {
            StaticValues.Instance().btPlayer.getPos().y = StaticValues.Instance().btPlayer.getPos().y + y;
        }
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