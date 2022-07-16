package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetComment {

@SerializedName("users_post_comment")
@Expose
public ArrayList<UsersPostComment> usersPostComment = null;
@SerializedName("message")
@Expose
public String message;

    @Override
    public String toString() {
        return "GetComment{" +
                "usersPostComment=" + usersPostComment +
                ", message='" + message + '\'' +
                '}';
    }
}