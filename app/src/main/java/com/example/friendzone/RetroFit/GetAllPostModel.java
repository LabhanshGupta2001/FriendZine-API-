package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllPostModel {

    @SerializedName("posts")
    @Expose
    public List<Post> posts = null;
    @SerializedName("message")
    @Expose
    public String message;

    @Override
    public String toString() {
        return "GetAllStoryModel{" +
                "posts=" + posts +
                ", message='" + message + '\'' +
                '}';
    }
}