package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

@SerializedName("user_data")
@Expose
public UserData userData;
@SerializedName("message")
@Expose
public String message;

    @Override
    public String  toString() {
        return "LoginModel{" +
                "userData=" + userData +
                ", message='" + message + '\'' +
                '}';
    }
}