package com.example.mju_mobile_computing_final.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mju_mobile_computing_final.DTO.UserInfo;
import com.example.mju_mobile_computing_final.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ChatFragment extends Fragment {
    private UserInfo user = UserInfo.getInstance();
    private Button btn_send;
    private Socket mSocket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        btn_send = v.findViewById(R.id.btn_send);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            mSocket = IO.socket(getResources().getString(R.string.server_url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.connect();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject chat = new JSONObject();
                try {
                    chat.put("displayName", user.getDisplayName());
                    chat.put("email", user.getEmail());
                    chat.put("content", "hello~!");
                } catch (JSONException e) {
                    return;
                }

                mSocket.emit("new message", chat);
            }
        });
    }

}
