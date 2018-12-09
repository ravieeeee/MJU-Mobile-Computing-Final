package com.example.mju_mobile_computing_final.View;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mju_mobile_computing_final.Event.OnMove;
import com.example.mju_mobile_computing_final.Global.GlobalApplication;
import com.example.mju_mobile_computing_final.R;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements AddingBuildingDialogFragment.AddingBuildingDialogListener {
    private Call<JsonObject> requestRandomColor;
    private static final String TAG = MapFragment.class.getName();
    private FloatingActionButton fab;
    private ViewGroup l_fragment_map;
    private ImageView icon_share, icon_map, icon_background;

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
        icon_map = view.findViewById(R.id.icon_map);
        icon_background = view.findViewById(R.id.icon_background);
        icon_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (icon_background.getVisibility() == View.VISIBLE) {
                    icon_background.setVisibility(View.INVISIBLE);
                } else {
                    icon_background.setVisibility(View.VISIBLE);
                }
            }
        });
        icon_share = view.findViewById(R.id.icon_share);
        icon_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View container = getActivity().getWindow().getDecorView();
                container.buildDrawingCache();
                Bitmap bitmap = container.getDrawingCache();
                String today = new SimpleDateFormat("HH:mm:ss").format(new Date());
                try {
                    File cachePath = new File(getContext().getCacheDir(), "images");
                    cachePath.mkdirs();
                    FileOutputStream stream = new FileOutputStream(cachePath + "/" + today + ".png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File imagePath = new File(getContext().getCacheDir(), "images");
                File newFile = new File(imagePath, today + ".png");
                Uri contentUri = FileProvider.getUriForFile(getContext(), "com.example.mju_mobile_computing_final.fileProvider", newFile);

                if (contentUri != null) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                }
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
                tv_building.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45f);
                tv_building.setPadding(20, 5, 20, 300);
                break;
            case "Medium":
                tv_building.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
                tv_building.setPadding(15, 5, 15, 200);
                break;
            case "Small":
                tv_building.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                tv_building.setPadding(10, 5, 10, 100);
                break;
        }
        tv_building.setText(buildingName);
        tv_building.setOnTouchListener(new OnMove());
        requestRandomColor.clone().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String hexCode
                        = "#" + response
                        .body()
                        .getAsJsonArray("colors")
                        .get(0)
                        .getAsJsonObject()
                        .get("hex")
                        .getAsString();
                try {
                    tv_building.setBackgroundColor(Color.parseColor(hexCode));
                } catch (Exception e) {
                    tv_building.setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        );
        l_fragment_map.addView(v, params);
        v.clearFocus();
    }
}
