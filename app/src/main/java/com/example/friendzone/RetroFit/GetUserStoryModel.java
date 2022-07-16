package com.example.friendzone.RetroFit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetUserStoryModel {

@SerializedName("story")
@Expose
public ArrayList<Story> story = null;

@SerializedName("message")
@Expose
public String message;

    @Override
    public String toString() {
        return "GetUserStoryModel{" +
                "story=" + story +
                ", message='" + message + '\'' +
                '}';
    }

}