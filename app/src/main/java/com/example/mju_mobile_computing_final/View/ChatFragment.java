package com.example.mju_mobile_computing_final.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mju_mobile_computing_final.DTO.Chatting;
import com.example.mju_mobile_computing_final.DTO.MyInfo;
import com.example.mju_mobile_computing_final.DTO.User;
import com.example.mju_mobile_computing_final.R;
import com.example.mju_mobile_computing_final.View.Utils.ChatRecyclerViewAdapter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatFragment extends Fragment {
    private MyInfo user = MyInfo.getInstance();
    private ImageView icon_send;
    private Socket mSocket;
    private EditText et_chat;
    private RecyclerView rv_chat;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private ArrayList<Chatting> chattingData;
    private static final String TAG = ChatFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        icon_send = v.findViewById(R.id.icon_send);
        et_chat = v.findViewById(R.id.et_chat);
        rv_chat = v.findViewById(R.id.rv_chat);
        rv_chat.setHasFixedSize(true);
        rv_chat.setLayoutManager(new GridLayoutManager(getContext(), 1));

        chattingData = new ArrayList<>();
        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(), chattingData);
        rv_chat.setAdapter(chatRecyclerViewAdapter);

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
        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        icon_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject chat = new JSONObject();
                try {
                    chat.put("photoUrl", user.getPhotoUrl());
                    chat.put("displayName", user.getDisplayName());
                    chat.put("email", user.getEmail());
                    chat.put("content", et_chat.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd h:mm a");
                    String date = sdf.format(new Date(System.currentTimeMillis()));
                    chat.put("date", date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mSocket.emit("new message", chat);
                et_chat.getText().clear();
            }
        });

    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        User user = new User(
                            data.getString("displayName"),
                            data.getString("email"),
                            Uri.parse(data.getString("photoUrl"))
                        );
                        String content = data.getString("content");
                        String date = data.getString("date");

                        Chatting chatting = new Chatting(user, content, date);
                        chattingData.add(chatting);
//                        chatRecyclerViewAdapter.notifyItemInserted(position);
                        chatRecyclerViewAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
}
