package com.example.mju_mobile_computing_final.DTO;

public class Chatting {
    private User user;
    private String chat;

    public Chatting(User user, String chat) {
        this.user = user;
        this.chat = chat;
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
}
