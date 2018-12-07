package com.example.mju_mobile_computing_final.View.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mju_mobile_computing_final.DTO.Chatting;
import com.example.mju_mobile_computing_final.DTO.User;
import com.example.mju_mobile_computing_final.Global.GlobalApplication;
import com.example.mju_mobile_computing_final.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Chatting> chattingData;
    private static final String TAG = ChatRecyclerViewAdapter.class.getName();

    public ChatRecyclerViewAdapter(Context context, ArrayList<Chatting> data) {
        this.mInflater = LayoutInflater.from(context);
        this.chattingData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_chat_user;
        private TextView tv_chat_user_name, tv_chat_user_email, tv_chat_content, tv_chat_date;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_chat_user = itemView.findViewById(R.id.iv_chat_user);
            tv_chat_user_name = itemView.findViewById(R.id.tv_chat_user_name);
            tv_chat_user_email = itemView.findViewById(R.id.tv_chat_user_email);
            tv_chat_content = itemView.findViewById(R.id.tv_chat_content);
            tv_chat_date = itemView.findViewById(R.id.tv_chat_date);
        }
    }

    @Override
    public void onBindViewHolder(ChatRecyclerViewAdapter.ViewHolder holder, int position) {
        User user = chattingData.get(position).getUser();
        String chat = chattingData.get(position).getChat();
        String date = chattingData.get(position).getDate();

        Picasso.with(GlobalApplication.context).load(user.getPhotoUrl()).into(holder.iv_chat_user);
        holder.tv_chat_user_name.setText(user.getDisplayName());
        holder.tv_chat_user_email.setText(user.getEmail());
        holder.tv_chat_content.setText(chat);
        holder.tv_chat_date.setText(date);
    }

    @Override
    public int getItemCount() {
        return chattingData.size();
    }
}
