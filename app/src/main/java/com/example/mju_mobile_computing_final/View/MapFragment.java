package com.example.mju_mobile_computing_final.View;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mju_mobile_computing_final.Event.OnMove;
import com.example.mju_mobile_computing_final.Global.GlobalApplication;
import com.example.mju_mobile_computing_final.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements AddingBuildingDialogFragment.AddingBuildingDialogListener {
    private Call<JsonObject> requestRandomColor;
    private static final String TAG = MapFragment.class.getName();
    private FloatingActionButton fab;
    private ViewGroup l_fragment_map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        l_fragment_map = view.findViewById(R.id.l_fragment_map);
        fab = view.findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddingBuildingDialogFragment fragment = new AddingBuildingDialogFragment();
                fragment.setTargetFragment(MapFragment.this, 0);
                fragment.show(getFragmentManager(), "AddingBuildingDialogFragment");
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRandomColor = GlobalApplication.service.getRandomColor();
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
        l_fragment_map.addView(v, params);
    }
}
