package com.example.mju_mobile_computing_final.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private MyInfo user = MyInfo.getInstance();
    private Button btn_send;
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
        btn_send = v.findViewById(R.id.btn_send);
        et_chat = v.findViewById(R.id.et_chat);
        rv_chat = v.findViewById(R.id.rv_chat);
        rv_chat.setHasFixedSize(true);
        rv_chat.setLayoutManager(new GridLayoutManager(getContext(), 1));

        chattingData = new ArrayList<>();
        chattingData.add(new Chatting(
                new User("편주영", "pyeonjy97@gmail.com",
                        Uri.parse("https://lh3.googleusercontent.com/a-/AN66SAy-yvnRC9gMWLu36QSNOCrhL7aFMtZspmhOGvHW")
                ), "init"
        ));
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

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject chat = new JSONObject();
                try {
                    chat.put("photoUrl", user.getPhotoUrl());
                    chat.put("displayName", user.getDisplayName());
                    chat.put("email", user.getEmail());
                    chat.put("content", et_chat.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mSocket.emit("new message", chat);
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
                        Chatting chatting = new Chatting(user, content);
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
