package com.example.pc.fragmentbase.Other;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by LP on 20-04-2017.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener{

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        StaticValues.Instance().globalPlayer.playerJump();
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }
}
