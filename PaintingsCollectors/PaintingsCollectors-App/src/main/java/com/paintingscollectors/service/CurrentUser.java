package com.paintingscollectors.service;

import com.paintingscollectors.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {

    private User user;

    public boolean loggedIn(){
        return this.user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }
}
