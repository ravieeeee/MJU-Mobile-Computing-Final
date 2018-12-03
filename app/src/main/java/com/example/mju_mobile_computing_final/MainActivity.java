package com.example.mju_mobile_computing_final;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.mju_mobile_computing_final.View.AddingBuildingDialogFragment;

public class MainActivity extends AppCompatActivity implements AddingBuildingDialogFragment.AddingBuildingDialogListener {
    private float dX, dY;
    private TextView tv_test;
    private FloatingActionButton fab;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddingBuildingDialogFragment fragment = new AddingBuildingDialogFragment();
                fragment.show(getSupportFragmentManager(), "AddingBuildingDialogFragment");
            }
        });

        tv_test = findViewById(R.id.tv_test);
        tv_test.setOnTouchListener(new View.OnTouchListener() {
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
        });
    }

    @Override
    public void onDialogPositiveClick(String buildingName) {
        Log.d(TAG, "buildingName: " + buildingName);
    }
}
