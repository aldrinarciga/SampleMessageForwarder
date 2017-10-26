package com.aldrinarciga.samplemessageforwarder.model;

/**
 * Created by aldrinarciga on 10/26/2017.
 */

public class TokenEvent {
    private String token;

    public TokenEvent(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
