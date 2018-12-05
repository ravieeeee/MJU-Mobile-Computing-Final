package com.example.mju_mobile_computing_final;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mju_mobile_computing_final.Event.OnMove;
import com.example.mju_mobile_computing_final.Global.GlobalApplication;
import com.example.mju_mobile_computing_final.View.AddingBuildingDialogFragment;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AddingBuildingDialogFragment.AddingBuildingDialogListener {
    private FloatingActionButton fab;
    private static final String TAG = MainActivity.class.getName();
    private Call<JsonObject> requestRandomColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestRandomColor = GlobalApplication.service.getRandomColor();

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddingBuildingDialogFragment fragment = new AddingBuildingDialogFragment();
                fragment.show(getSupportFragmentManager(), "AddingBuildingDialogFragment");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(String buildingName, String size) {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_tv, null);

        final TextView tv_building = v.findViewById(R.id.tv_building);
        switch (size) {
            case "Big":
                tv_building.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60f);
                break;
            case "Medium":
                tv_building.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35f);
                break;
            case "Small":
                tv_building.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                break;
        }
        tv_building.setText(buildingName);
        tv_building.setOnTouchListener(new OnMove());
        requestRandomColor.clone().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "responseBody:" + response.body().toString());

                String hexCode
                    = "#" + response
                        .body()
                        .getAsJsonArray("colors")
                        .get(0)
                        .getAsJsonObject()
                        .get("hex")
                        .getAsString();
                tv_building.setBackgroundColor(Color.parseColor(hexCode));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        );
        ViewGroup vg = findViewById(R.id.l_activity_main);
        vg.addView(v, params);
    }
}
