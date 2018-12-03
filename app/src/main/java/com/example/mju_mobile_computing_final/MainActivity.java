package com.example.mju_mobile_computing_final;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mju_mobile_computing_final.Event.OnMove;
import com.example.mju_mobile_computing_final.View.AddingBuildingDialogFragment;

public class MainActivity extends AppCompatActivity implements AddingBuildingDialogFragment.AddingBuildingDialogListener {
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
        tv_test.setOnTouchListener(new OnMove());
    }

    @Override
    public void onDialogPositiveClick(String buildingName) {
        Log.d(TAG, "buildingName: " + buildingName);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tv, null);

        TextView tv_building = v.findViewById(R.id.tv_building);
        tv_building.setText(buildingName);
        tv_building.setOnTouchListener(new OnMove());

        ViewGroup vg = findViewById(R.id.l_activity_main);
        vg.addView(v, 0);
    }
}
