package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllStory {

    @SerializedName("story")
    @Expose
    public ArrayList<Story2> story = null;
    @SerializedName("message")
    @Expose
    public String message;

    @Override
    public String toString() {
        return "GetAllStory{" +
                "story=" + story +
                ", message='" + message + '\'' +
                '}';
    }
}