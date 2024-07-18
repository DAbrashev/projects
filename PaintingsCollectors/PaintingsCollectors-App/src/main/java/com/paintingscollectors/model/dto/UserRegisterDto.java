package com.paintingscollectors.model.dto;

import org.hibernate.annotations.Comment;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserRegisterDto {
    @Size(min = 3,max = 20)
    private String username;

    @Email
    private String email;

    @Size(min = 3,max = 20)
    private String password;

    @Size(min = 3,max = 20)
    private String confirmPassword;

    public UserRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
