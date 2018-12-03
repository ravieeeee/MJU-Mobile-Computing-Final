package com.example.mju_mobile_computing_final.Event;

import android.view.MotionEvent;
import android.view.View;

public class OnMove implements View.OnTouchListener {
    private float dX, dY;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                view.animate()
                    .x(event.getRawX() + dX)
                    .y(event.getRawY() + dY)
                    .setDuration(0)
                    .start();
                break;
            default:
                return false;
        }
        return true;
    }
}
