package com.pixium.clinitick.api.models.auth;

import com.pixium.clinitick.api.models.User;

public class AuthResponse {
    private String message;

    private String token;

    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
