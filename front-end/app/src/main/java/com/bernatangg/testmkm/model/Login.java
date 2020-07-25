package com.bernatangg.testmkm.model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("username")
    private String username;

    @SerializedName("session")
    private String session;

    public Login(String username, String session) {
        this.username = username;
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
