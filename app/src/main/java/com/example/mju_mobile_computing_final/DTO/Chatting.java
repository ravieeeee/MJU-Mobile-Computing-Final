package com.example.mju_mobile_computing_final.DTO;

import java.util.Date;

public class Chatting {
    private User user;
    private String chat;
    private String date;

    public Chatting(User user, String chat, String date) {
        this.user = user;
        this.chat = chat;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
