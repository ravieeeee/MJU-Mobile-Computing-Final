package com.example.mju_mobile_computing_final.DTO;

import android.net.Uri;

public class UserInfo {
    private static UserInfo instance = new UserInfo();

    private String displayName = null;
    private String email = null;
    private Uri photoUrl = null;

    public static UserInfo getInstance() {
        return instance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }
}
